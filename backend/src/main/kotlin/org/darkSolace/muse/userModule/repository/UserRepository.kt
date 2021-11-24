package org.darkSolace.muse.userModule.repository

import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTag
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * CRUD-Repository for the [User] entity.
 */
@Repository
interface UserRepository : CrudRepository<User, Long> {
    /**
     * Retrieves a user with given [username] from the database.
     *
     * @param username the username to look up the [User]
     * @return The [User] or null
     */
    fun findByUsername(username: String): User?

    /**
     * Retrieves all [User]s with a given [Role] from the database.
     *
     * @param role the required [Role]
     * @return a [List] of [User]s having the required [Role]
     */
    fun findAllByRole(role: Role): List<User>

    /**
     * Retrieves all [User]s with a given [UserTag] from the database.
     *
     * @param tag the required [UserTag]
     * @return a [List] of [User]s having the required [UserTag]
     */
    fun findAllByUserTags(tag: UserTag): List<User>

    /**
     * Checks if a [User] with a given username already exists
     *
     * @param username The username to check
     * @return [Boolean] weather the [User] already exists
     */
    fun existsByUsernameIgnoreCase(username: String): Boolean

    /**
     * Checks if an eMail-Address is already in use by a [User]
     *
     * @param email The email-address to check
     * @return [Boolean] weather the email-address is already in use
     */
    fun existsByEmailIgnoreCase(email: String): Boolean
}
