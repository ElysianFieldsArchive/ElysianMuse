package org.darkSolace.muse.security.service

import org.darkSolace.muse.user.repository.UserRepository
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
     * Loads a [org.darkSolace.muse.user.model.User] and creates the corresponding [UserDetails]
     *
     * @param username the username
     * @return the created [UserDetails]
     */
    override fun loadUserByUsername(username: String?): UserDetails {
        require(!username.isNullOrEmpty())

        val user = userRepository.findByUsername(username) ?: throw IllegalArgumentException("Invalid username")

        return org.darkSolace.muse.security.model.UserDetails(user)
    }
}
