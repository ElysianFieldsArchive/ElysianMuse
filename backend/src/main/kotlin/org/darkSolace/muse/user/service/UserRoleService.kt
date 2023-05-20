package org.darkSolace.muse.user.service

import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.SuspensionHistoryRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for [Role] related tasks when working with [User]s.
 *
 * @see UserService
 */
@Service
class UserRoleService(
    @Autowired val userRepository: UserRepository,
    @Autowired val suspensionHistoryRepository: SuspensionHistoryRepository
) {
    /**
     * Suspends the provided [User] and persists it in the database.
     *
     * @param user the [User] to be suspended
     * @return the suspended [User] or `null` if the [User] was not found
     */
    @Transactional
    fun suspendUser(user: User): User? {
        val userToSuspend = if (user.id == null) {
            // trying to find user
            userRepository.findByUsername(user.username)
        } else
            user
        return suspendUser(userToSuspend?.id ?: -1)
    }

    /**
     * Suspends the provided [User], identified by its id, and persists it in the database.
     * Also creates a [SuspensionHistoryEntry] if it doesn't exist.
     *
     * @param id of the [User] to be suspended
     * @return the suspended [User] or `null` if the [User] was not found
     */
    @Transactional
    fun suspendUser(id: Long): User? {
        val user = userRepository.findByIdOrNull(id) ?: return null
        return if (user.role == Role.SUSPENDED) {
            //check if a suspensionHistoryEntry already exists, creat one if not
            if (suspensionHistoryRepository.getByUserAndAcceptedDateIsNull(user) == null) {
                val suspension = SuspensionHistoryEntry(null, user)
                suspensionHistoryRepository.save(suspension)
            }
            user
        } else {
            val suspension = SuspensionHistoryEntry(null, user)
            suspensionHistoryRepository.save(suspension)
            changeRole(user, Role.SUSPENDED)
        }
    }

    /**
     * Changes the role of a [User] and persists it in the database.
     *
     * @param user the [User] to modify
     * @param role the new [Role] of this [User]
     * @return the modified [User] or `null` if the [User] was not found
     */
    @Transactional
    fun changeRole(user: User, role: Role): User? {
        val changedUser = userRepository.findByUsername(user.username) ?: return null

        changedUser.role = role
        userRepository.save(changedUser)
        return changedUser
    }
}
