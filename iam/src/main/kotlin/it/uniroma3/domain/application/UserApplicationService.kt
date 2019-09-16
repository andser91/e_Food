package it.uniroma3.domain.application


import it.uniroma3.commonauth.CreateUserResponse
import it.uniroma3.domain.model.User
import it.uniroma3.domain.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserApplicationService(private val userService: UserService){ //private val eventPublisher: EventPublisher){

    var logger: Logger = LoggerFactory.getLogger(UserApplicationService::class.java)!!

    fun signUpUser(username: String, password: String, email: String, firstname: String, lastname: String) : CreateUserResponse {
        val createUserResponse = CreateUserResponse()
        createUserResponse.username = username
        createUserResponse.password = password
        var user = userService.createUser(username, password, email, firstname, lastname)
        user=userService.toUser(user)
        createUserResponse.id = user.id
//        val sellerSignedUpEvent = SellerSignedUpEvent(user.id, user.username, user.email,LocalDateTime.now())
//        eventPublisher.publish(sellerSignedUpEvent)
//        logger.info("seller saved {} and message pubblished {}",user,sellerSignedUpEvent)
        return createUserResponse
    }

}