package it.uniroma3.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import it.uniroma3.commonauth.JWTConfig
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import java.sql.Date
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtUsernameAndPasswordAuthenticationFilter(// We use auth manager to validate the user credentials
        private val authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {


    init {

        // By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
        // In our case, we use "/auth". So, we need to override the defaults.
        this.setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher(JWTConfig.getInstance().uri, "POST"))
    }

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {

        try {

            // 1. Get credentials from request
            val creds : UserCredentials = ObjectMapper().readValue(request.inputStream, UserCredentials::class.java)

            // 2. Create auth object (contains credentials) which will be used by auth manager
            val authToken = UsernamePasswordAuthenticationToken(
                    creds.username, creds.password, emptyList())

            // 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authManager.authenticate(authToken)

        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    // Upon successful authentication, generate a token.
    // The 'auth' passed to successfulAuthentication() is the current authenticated user.
    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain?,
                                          auth: Authentication) {

        val now = System.currentTimeMillis()
        val token = Jwts.builder()
                .setSubject(auth.name)
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", auth.authorities.stream()
                        .map { it.authority}.collect(Collectors.toList()))
                .setIssuedAt(Date(now))
                .setExpiration(Date(now + JWTConfig.getInstance().expiration * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, JWTConfig.getInstance().secret.toByteArray())
                .compact()

        // Add token to header
        response.addHeader(JWTConfig.getInstance().header, JWTConfig.getInstance().prefix + token)
    }

    // A (temporary) class just to represent the user credentials
    private class UserCredentials {
        val username: String? = null
        val password: String? = null
    }
}
