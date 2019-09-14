package it.uniroma3.config

import com.exeest.commonauth.JwtTokenAuthenticationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
class WebSecurityConfiguration (@Qualifier("userService") private val userDetailsService: UserDetailsService,
                                private val passwordEncoderAndMatcher: PasswordEncoderAndMatcherConfig) : WebSecurityConfigurerAdapter(){


    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/user/").authenticated()
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