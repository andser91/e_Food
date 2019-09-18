package it.uniroma3.config

import io.micrometer.core.instrument.MeterRegistry
import it.uniroma3.commonauth.JwtTokenAuthenticationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.firewall.StrictHttpFirewall
import org.springframework.security.web.firewall.HttpFirewall



@EnableWebSecurity
@Configuration
class WebSecurityConfiguration (@Qualifier("userService") private val userDetailsService: UserDetailsService,
                                private val passwordEncoderAndMatcher: PasswordEncoderAndMatcherConfig,
                                private val meterRegistry: MeterRegistry) : WebSecurityConfigurerAdapter(){


    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .cors().and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterAfter(JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .addFilter(JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), meterRegistry))
    }


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoderAndMatcher)
    }
}