package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for [Role] related tasks when working with [User]s.
 *
 * @see UserService
 */
@Service
class UserRoleService(@Autowired val userRepository: UserRepository) {

    /**
     * Suspends the provided user and persists it in the database
     *
     * @param user the [User] to be suspended
     * @return the suspended [User] or null if the [User] was not found
     */
    @Transactional
    fun suspendUser(user: User): User? {
        return changeRole(user, Role.SUSPENDED)
    }

    /**
     * Changes the role of an [User] and persists it in the database
     *
     * @param user the [User] to modify
     * @param role the new [Role] of this [User]
     * @return the modified [User] or null if the [User] was not found
     */
    @Transactional
    fun changeRole(user: User, role: Role): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }

        return if (changedUser != null) {
            changedUser.role = role
            userRepository.save(changedUser)
            changedUser
        } else
            null
    }
}
