package org.darkSolace.muse.securityModule.service

import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

/**
 * [Service] to handle everything in regard to [UserDetails].
 *
 * @see [UserDetailsService]
 */
@Service
class UserDetailsService(@Autowired val userRepository: UserRepository) : UserDetailsService {

    /**
     * Loads a [org.darkSolace.muse.userModule.model.User] and creates the corresponding [UserDetails]
     *
     * @param username the username
     * @return the created [UserDetails]
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrEmpty()) throw IllegalArgumentException("Empty username")
        val user = userRepository.findByUsername(username) ?: throw IllegalArgumentException("Invalid username")

        return org.darkSolace.muse.securityModule.model.UserDetails(user)
    }
}
