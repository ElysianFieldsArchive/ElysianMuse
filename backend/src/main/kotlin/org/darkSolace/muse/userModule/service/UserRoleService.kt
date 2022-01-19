package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.SuspensionHistoryEntry
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.repository.SuspensionHistoryRepository
import org.darkSolace.muse.userModule.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

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
        return changeRole(user, Role.SUSPENDED)
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

    /**
     * Retrieves the confirmation code for a users open [SuspensionHistoryEntry].
     * The user is identified by its username.
     *
     * @param username the username
     * @return the confirmation code
     */
    fun suspensionCodeForUsername(username: String): String? {
        val user = userRepository.findByUsername(username) ?: return null
        return suspensionHistoryRepository.getByUserAndAcceptedDateIsNull(user)?.confirmationCode
    }

    /**
     * Confirms the acceptance of a [SuspensionHistoryEntry], identified by its confirmation code.
     *
     * @param confirmationCode the confirmation code belonging to the [SuspensionHistoryEntry] to be accepted
     * @return boolean if the acceptance was successful
     */
    fun confirmSuspension(confirmationCode: String): Boolean {
        val suspension = suspensionHistoryRepository.getByConfirmationCode(confirmationCode) ?: return false
        changeRole(suspension.user, Role.MEMBER)
        suspension.acceptedDate = Date()
        suspensionHistoryRepository.save(suspension)
        return true
    }

    /**
     * Retrieves the suspension history of a given user, identified by its id.
     *
     * @param id id of the user
     * @return List of [SuspensionHistoryEntry]s
     */
    fun getSuspensionHistory(id: Long): List<SuspensionHistoryEntry> {
        val user = userRepository.findByIdOrNull(id) ?: return emptyList()
        return getSuspensionHistory(user)
    }

    /**
     * Retrieves the suspention history of a given user.
     *
     * @param user the user
     * @return List of [SuspensionHistoryEntry]s
     */
    fun getSuspensionHistory(user: User): List<SuspensionHistoryEntry> {
        return suspensionHistoryRepository.findAllByUserOrderBySuspendedDate(user)
    }

    /**
     * Retrieves all currently suspended users
     *
     * @return List of currently suspended [User]s
     */
    fun getAllCurrentlySuspendedUsers(): List<User> =
        suspensionHistoryRepository.findAll()
            .filter { it.acceptedDate == null }
            .map { it.user }
            .distinct()

}
