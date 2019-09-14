package it.uniroma3.config

import it.uniroma3.domain.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.stream.Collectors

class WebSecurityUser (private val user: User) : UserDetails, Serializable {


    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.roles
                .stream()
                .map { role -> SimpleGrantedAuthority(role.toString())}
                .collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !user.credentialsExpired
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return !user.accountExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return !user.accountLocked
    }





}