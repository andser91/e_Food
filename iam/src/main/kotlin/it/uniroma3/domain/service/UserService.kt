package it.uniroma3.domain.service


import it.uniroma3.config.WebSecurityUser
import it.uniroma3.domain.model.RoleName
import it.uniroma3.domain.model.User
import it.uniroma3.domain.repository.RoleRepository
import it.uniroma3.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (private val userRepository: UserRepository,
                   private val roleRepository: RoleRepository) : UserDetailsService {


    override fun loadUserByUsername(username: String): UserDetails {
        val findByUserName = userRepository.findByUsername(username)
        return WebSecurityUser(findByUserName)
    }

    fun createUser(username: String, password: String,
                   email : String, firstName : String, lastName: String) : User {
        val passwordEncr = BCryptPasswordEncoder().encode(password)
        var user = User(firstName, lastName, username, email, passwordEncr)
        user=userRepository.save(user)
        return user
    }

    fun toUser(user: User) : User {
        val role = roleRepository.findByName(RoleName.User.name)
        val newRoles = HashSet(user.roles)
        newRoles.add(role)
        user.roles=newRoles
        return userRepository.save(user)
    }

}

