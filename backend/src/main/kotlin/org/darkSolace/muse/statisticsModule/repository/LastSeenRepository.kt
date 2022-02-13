package org.darkSolace.muse.statisticsModule.repository

import org.darkSolace.muse.statisticsModule.model.LastSeenEntry
import org.darkSolace.muse.userModule.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * CRUD-Repository for the [LastSeenRepository] entity.
 */
@Repository
interface LastSeenRepository : CrudRepository<LastSeenEntry, User> {
    /**
     * Retrieves all [LastSeenEntry] before a specific timestamp, used to determine timed-out sessions
     *
     * @param timeout - [Date], used to filter [LastSeenEntry]s older than the specified date
     * @return List of all [LastSeenEntry]s older than the provided timeout [Date]
     */
    fun findAllByLastSeenBefore(timeout: Date): List<LastSeenEntry>

    /**
     * Retrieves a [LastSeenEntry] for a given [User]
     *
     * @param user - the [User] to be used to look up the [LastSeenEntry]
     * @return the [LastSeenEntry] for the specified [User] or `null`
     */
    fun findByUser(user: User): LastSeenEntry?

    /**
     * Retrieves a [LastSeenEntry] for a given session ID
     *
     * @param jSession - [String] containing a session ID used to identify the [LastSeenEntry]
     * @return the [LastSeenEntry] for the specified session ID or `null` if no [LastSeenEntry] was found
     */
    fun findByjSession(jSession: String?): LastSeenEntry?

    /**
     * Deletes a [LastSeenEntry] for a given [User]
     *
     * @param user - the [User] to be used to look up the [LastSeenEntry]
     * @see [org.darkSolace.muse.userModule.service.UserService.deleteUser]
     */
    fun deleteByUser(deletedUser: User)
}
