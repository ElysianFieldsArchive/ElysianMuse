package org.darkSolace.muse.user.service

import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.mail.repository.MailQueueRepository
import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserSettings
import org.darkSolace.muse.user.repository.UserRepository
import org.darkSolace.muse.user.repository.UserSettingsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Service class for [User] related tasks.
 *
 * @see UserRoleService
 * @see UserTagService
 */
@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired private val userSettingsRepository: UserSettingsRepository,
    @Autowired private val lastSeenRepository: LastSeenRepository,
    @Autowired private val mailService: MailService,
    @Autowired private val mailQueueRepository: MailQueueRepository,
) {

    /**
     * Creates and persists a [User] in the database
     * Password is hashed in the process
     */
    fun createUser(user: User): User? {
        //check if user already exists
        if (userRepository.existsByUsernameIgnoreCase(user.username) ||
            userRepository.existsByEmailIgnoreCase(user.email)
        ) {
            return null
        }

        //hash the password before saving the user
        user.password = BCrypt.hashpw(user.password, user.salt)

        //generate email confirmation string
        user.emailConfirmationCode = UUID.randomUUID().toString()

        userRepository.save(user)

        //user saved - send confirmation email
        mailService.sendEMailConfirmationMail(user)
        return user
    }

    /**
     * Finds a [User] by its ID
     *
     * @param id the ID as [Long]
     * @return the found [User] or `null`
     */
    fun getById(id: Long) = userRepository.findByIdOrNull(id)

    /**
     * Returns a [List] of all [User]s
     *
     * @return a [List] of all [User]s - might be empty if no [User]s exist
     */
    fun getAll(): List<User> = userRepository.findAll().toList()

    /**
     * Returns a [List] of all [User]s with a given [Role]
     *
     * @param role the [Role] to filter by
     * @return a [List] of all [User]s with the given [Role]- might be empty if no [User]s exist with this [Role]
     */
    fun getAllWithRole(role: Role) = userRepository.findAllByRole(role)


    /**
     * Deletes a [User] from the database
     *
     * @param user the [User] to be deleted
     *
     * TODO: deal with created content by this user
     *  * assign stories and chapters to orphan accounts
     *  * replace username in private messages and comments and
     *  * other occurrences where a user might be involved
     */
    @Transactional
    fun deleteUser(user: User) {
        val deletedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username) ?: return
            } else {
                user
            }

        mailQueueRepository.deleteAllByMail_Recipient(deletedUser)
        lastSeenRepository.deleteByUser(deletedUser)
        userRepository.delete(deletedUser)
    }

    /**
     * Deletes a [User] from the database
     *
     * @param id of the [User] to be deleted
     */
    @Transactional
    fun deleteUser(id: Long) {
        val user = userRepository.findByIdOrNull(id) ?: return
        deleteUser(user)
    }

    /**
     * Replaces the [UserSettings] of a given [User] and persists it
     *
     * @param user the [User] to be modified
     * @param settings the new [UserSettings] to be applied
     * @return the modified [User] or `null` if the [User] does not exist
     */
    @Transactional
    fun updateUserSettings(user: User, settings: UserSettings): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }
        if (changedUser == null)
            return null

        // userSettings exchanged - no diff is created. Old settings are deleted once the new ones are set
        val oldSettings = changedUser.userSettings
        userSettingsRepository.save(settings)
        changedUser.userSettings = settings
        userRepository.save(changedUser)
        userSettingsRepository.delete(oldSettings)

        return changedUser
    }

    /**
     * Creates a user from a [SignUpRequest]
     *
     * @param signUpRequest The [SignUpRequest] containing all required information
     * @return the created [User]
     */
    fun createUserFromSignUpRequest(signUpRequest: SignUpRequest): User? {
        val user = User(
            username = signUpRequest.username,
            password = signUpRequest.password,
            email = signUpRequest.email
        )

        return createUser(user)
    }

    /**
     * Updates the lastLogInDate timestamp of a given user.
     *
     * @param user the [User] to update
     */
    fun updateLastLogin(user: User) {
        user.lastLogInDate = Date()
        userRepository.save(user)
    }
}
