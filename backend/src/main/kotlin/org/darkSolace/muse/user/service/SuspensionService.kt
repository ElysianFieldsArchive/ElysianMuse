package org.darkSolace.muse.user.service

import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.SuspensionHistoryEntry
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.SuspensionHistoryRepository
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class SuspensionService(
    @Autowired val userRoleService: UserRoleService,
    @Autowired val userRepository: UserRepository,
    @Autowired val suspensionHistoryRepository: SuspensionHistoryRepository
) {
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
        userRoleService.changeRole(suspension.user, Role.MEMBER)
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
     * Retrieves the suspension history of a given user.
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
