package it.uniroma3.config

import it.uniroma3.commonauth.JwtTokenAuthenticationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class WebSecurityConfiguration (@Qualifier("userService") private val userDetailsService: UserDetailsService,
                                private val passwordEncoderAndMatcher: PasswordEncoderAndMatcherConfig) : WebSecurityConfigurerAdapter(){


    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterAfter(JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
    }


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderAndMatcher)
    }

}