package org.darkSolace.muse.configuration

import org.darkSolace.muse.security.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class AuthenticationConfiguration(
    @Autowired private val userDetailsService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder,
) {
    /**
     * Configures the [AuthenticationManager], via [AuthenticationManagerBuilder],
     * to use [UserDetailsService] and [BCryptPasswordEncoder]
     *
     * @return configured [AuthenticationManager]
     */
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val auth = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder)
        return auth.build()
    }
}
