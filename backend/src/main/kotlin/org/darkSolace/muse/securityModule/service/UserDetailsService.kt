package org.darkSolace.muse.securityModule.service

import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService : UserDetailsService {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrEmpty()) throw IllegalArgumentException("Empty username")
        val user = userRepository.findByUsername(username) ?: throw IllegalArgumentException("Invalid username")

        return org.darkSolace.muse.securityModule.model.UserDetails(user)
    }
}
