package it.uniroma3.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAndMatcherConfig : PasswordEncoder {
    override fun encode(rawPassword: CharSequence?): String {
        return BCryptPasswordEncoder().encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
    }
}