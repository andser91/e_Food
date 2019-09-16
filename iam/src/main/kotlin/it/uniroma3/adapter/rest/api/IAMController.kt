package it.uniroma3.adapter.rest.api


import it.uniroma3.adapter.rest.dto.UserSignUpRequest
import it.uniroma3.commonauth.JWTConfig
import it.uniroma3.commonauth.JWTTokenBroker
import it.uniroma3.domain.application.UserApplicationService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
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

}
