package org.darkSolace.muse.lastSeen.service

import org.darkSolace.muse.lastSeen.model.LastSeenEntry
import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

/**
 * Service class for [LastSeenEntry] related tasks.
 */
@Service
class LastSeenService(
    @Autowired val userRepository: UserRepository,
    @Autowired val lastSeenRepository: LastSeenRepository,
    @Value("\${session.timeInMinutes}") val sessionTimeInMinutes: String
) {

    /**
     * Updates a [LastSeenEntry], identified either by username (if the user is logged-in) or by session ID
     *
     * @param username - the username to identify the [User]
     * @param jSession - the session ID to identify a visitor
     */
    fun updateLastSeen(username: String?, jSession: String?) {
        if (username != null) {
            val user = userRepository.findByUsername(username) ?: return
            updateLastSeenByUser(user, jSession)
        } else {
            if (jSession == null) return
            updateLastSeenByJSession(jSession)
        }
    }

    /**
     * Internal method to update a [LastSeenEntry] by [User].
     * If a [LastSeenEntry] without a username present already exists, a (pre login) [LastSeenEntry] will be appended.
     *
     * @param user - the [User] to be used to update the [LastSeenEntry]
     * @param jSession - the session ID to be used to append a username to an existing [LastSeenEntry]
     */
    private fun updateLastSeenByUser(user: User, jSession: String?) {
        val lastSeenEntry = lastSeenRepository.findByUser(user)
        if (lastSeenEntry == null) {
            //get entry if there is already one for the session or create a new one
            val newEntry = (lastSeenRepository.findByjSession(jSession) ?: LastSeenEntry()).apply {
                this.user = user
                this.jSession = jSession
            }

            lastSeenRepository.save(newEntry)
        } else {
            lastSeenEntry.lastSeen = Date()
            lastSeenEntry.jSession = jSession
            lastSeenRepository.save(lastSeenEntry)
        }
    }

    /**
     * Internal method to update a [LastSeenEntry] identified by a session ID.
     *
     * @param jSession - the session ID to be used to update the [LastSeenEntry]
     */
    private fun updateLastSeenByJSession(jSession: String?) {
        val lastSeenEntry = lastSeenRepository.findByjSession(jSession)
        if (lastSeenEntry == null) {
            val newEntry = LastSeenEntry().apply {
                this.jSession = jSession
            }
            lastSeenRepository.save(newEntry)
        } else {
            lastSeenEntry.lastSeen = Date()
            lastSeenRepository.save(lastSeenEntry)
        }
    }

    /**
     * Returns the number of currently active visitors (logged-in and not)
     *
     * @return number of active visitors
     */
    fun getOnlineUserCount(): Long {
        return lastSeenRepository.count()
    }

    /**
     * Returns a [List] of currently active, logged-in users
     *
     * @return [List] of currently active, logged-in users
     */
    fun getOnlineUsers(): List<User> {
        return lastSeenRepository.findAll().mapNotNull { it.user?.toPublicUser() }
    }

    /**
     * Removes timed-out [LastSeenEntry]s from the database
     * This method is executed every 30 seconds
     */
    @Scheduled(cron = "0/30 * * * * *")
    @Transactional
    fun prune() {
        val timeOutDate = Date.from(Instant.now().minusMillis(sessionTimeInMinutes.minutesToMilliSeconds()))
        val timedOutUsers = lastSeenRepository.findAllByLastSeenBefore(timeOutDate)
        lastSeenRepository.deleteAll(timedOutUsers)
    }

    /**
     * Retrieves a [LastSeenEntry] for a given session ID
     *
     * @param sessionId - [String] containing a session ID used to identify the [LastSeenEntry]
     * @return the [LastSeenEntry] for the specified session ID or `null` if no [LastSeenEntry] was found
     */
    fun getBySession(sessionId: String?) = lastSeenRepository.findByjSession(sessionId)

    /**
     * Retrieves a [LastSeenEntry] for a given [User], identified by its username
     *
     * @param userName - the username of a [User] to be used to look up the [LastSeenEntry]
     * @return the [LastSeenEntry] for the specified [User] or `null`
     */
    fun getByUsername(userName: String): LastSeenEntry? {
        val user = userRepository.findByUsername(userName) ?: return null
        return lastSeenRepository.findByUser(user)
    }

}

private const val SECONDS_PER_MINUTE = 60L
private const val MILLIS_PER_SECOND = 1000L

/**
 * Helper to convert a String, representing a whole number of minutes, into milliseconds
 *
 * @return number of milliseconds for the given value
 */
private fun String.minutesToMilliSeconds() = this.toInt() * SECONDS_PER_MINUTE * MILLIS_PER_SECOND
