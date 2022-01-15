package org.darkSolace.muse.userModule.service

import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.userModule.model.*
import org.darkSolace.muse.userModule.repository.AvatarRepository
import org.darkSolace.muse.userModule.repository.UserRepository
import org.darkSolace.muse.userModule.repository.UserSettingsRepository
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
    @Autowired val avatarRepository: AvatarRepository,
    @Autowired val userSettingsRepository: UserSettingsRepository
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

        userRepository.save(user)
        return user
    }

    /**
     * Changes the [Avatar] for the specified [User] and persists it in the database.
     *
     * @param user the [User] to modify
     * @param avatar the new [Avatar]
     * @return the modified [User] or `null` if the [User] was not found
     */
    @Transactional
    fun changeAvatar(user: User, avatar: Avatar): User? {
        val changedUser =
            if (user.id == null) {
                userRepository.findByUsername(user.username)
            } else {
                user
            }
        if (changedUser == null)
            return null

        // just changes the avatar blob if an avatar already exists
        if (changedUser.avatar == null) {
            avatarRepository.save(avatar)
            changedUser.avatar = avatar
            userRepository.save(changedUser)
        } else {
            changedUser.avatar?.avatarBlob = avatar.avatarBlob
            userRepository.save(changedUser)
        }

        return changedUser
    }

    /**
     * Finds a [User] by its ID
     *
     * @param id the ID as [Long]
     * @return the found [User] or `null`
     */
    @Transactional
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
     * Returns a [List] of all [User]s with a given [UserTag]
     *
     * @param tag the [UserTag] to filter by
     * @return a [List] of all [User]s with the given [UserTag]- might be empty if no [User]s exist with this [UserTag]
     */
    fun getAllWithUserTag(tag: UserTag) = userRepository.findAllByUserTags(tag)

    /**
     * Deletes a [User] from the database
     *
     * @param user the [User] to be deleted
     *
     * TODO: deal with created content by this user
     *  * assign stories and chapters to orphan accounts
     *  * replace username in private messages and comments
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

        userRepository.delete(deletedUser)
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
     */
    fun createUserFromSignUpRequest(signUpRequest: SignUpRequest) {
        val user = User(
            username = signUpRequest.username,
            password = signUpRequest.password,
            email = signUpRequest.email
        )

        createUser(user)
    }

    fun updateLastLogin(user: User) {
        user.lastLogInDate = Date()
        userRepository.save(user)
    }
}
