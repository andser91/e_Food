package it.uniroma3.adapter.rest.api

import com.exeest.commonauth.JWTConfig
import com.exeest.commonauth.JWTTokenBroker
import it.uniroma3.adapter.rest.dto.UserInfoResponse
import it.uniroma3.adapter.rest.dto.UserSignUpRequest
import it.uniroma3.domain.application.UserApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/user")
class IAMController (private val userApplicationService: UserApplicationService) {

    companion object{
        const val SECURITY_CONTEXT="SPRING_SECURITY_CONTEXT"
    }

    @PostMapping("/sign-up")
    fun signUp(request: HttpServletRequest, response: HttpServletResponse,
               @RequestBody userSignUpRequest : UserSignUpRequest) {
        try {
            userApplicationService.signUpUser(userSignUpRequest.username, userSignUpRequest.password,
                    userSignUpRequest.email, userSignUpRequest.firstname, userSignUpRequest.lastname)
        } catch (ex:Exception){
            response.status=HttpStatus.CONFLICT.value()
        }
        request.login(userSignUpRequest.username,userSignUpRequest.password)
        val auth = SecurityContextHolder.getContext().authentication
        response.addHeader(JWTConfig.getInstance().header,
                JWTConfig.getInstance().prefix + JWTTokenBroker.getInstance().getToken(auth))
    }

    @GetMapping("/prova")
    fun prova(): String{
        return "prova"
    }


    @GetMapping
    fun getInfoSession(request: HttpServletRequest): UserInfoResponse {
        return UserInfoResponse(request.userPrincipal.name,SecurityContextHolder.getContext().authentication.authorities.toString())
    }

    @GetMapping("/role/{role}")
    fun isAuthenticatedWithRole(request: HttpServletRequest, @PathVariable role : String): ResponseEntity<Any> {
        val securityContext = request.session.getAttribute(SECURITY_CONTEXT) as SecurityContextImpl
        val authorities = securityContext.authentication.authorities
        if (authorities.map { (it as SimpleGrantedAuthority).authority }.contains(role))
            return ResponseEntity(HttpStatus.OK)
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
}
