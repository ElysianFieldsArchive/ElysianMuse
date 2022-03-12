package org.darkSolace.muse.lastSeen

import org.darkSolace.muse.lastSeen.model.LastSeenEntry
import org.darkSolace.muse.lastSeen.repository.LastSeenRepository
import org.darkSolace.muse.lastSeen.service.LastSeenService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

/**
 * session.time is set to 1 minute for those test
 */
internal class LastSeenServiceTests : TestBase() {
    @Autowired
    lateinit var lastSeenRepository: LastSeenRepository

    @Autowired
    lateinit var lastSeenService: LastSeenService

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @Order(1)
    fun updateLastSeen() {
        val entry = LastSeenEntry().apply {
            lastSeen = Date()
            jSession = "1234"
        }
        lastSeenRepository.save(entry)
        Thread.sleep(2000)
        lastSeenService.updateLastSeen(null, "1234")

        val updatedEntry = lastSeenRepository.findByjSession("1234")

        Assertions.assertNotNull(updatedEntry)
        Assertions.assertNotEquals(entry.lastSeen, updatedEntry?.lastSeen)
    }

    @Test
    @Order(2)
    fun getOnlineUserCount() {
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            jSession = "1234"
        })
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            jSession = "2345"
        })
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            jSession = "3456"
        })

        val count = lastSeenService.getOnlineUserCount()
        Assertions.assertEquals(3, count)
    }

    @Test
    @Order(3)
    fun getOnlineUsers() {
        val user1 = userRepository.save(User(null, "test", "1234", "12", "test@test.com"))
        val user2 = userRepository.save(User(null, "test2", "1234", "12", "test2@test.com"))
        val user3 = userRepository.save(User(null, "test3", "1234", "12", "test3@test.com"))
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            user = user1
        })
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            user = user2
        })
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            user = user3
        })

        val users = lastSeenService.getOnlineUsers()
        Assertions.assertEquals(3, users.size)
        Assertions.assertTrue(users.map { it.username }.containsAll(listOf("test", "test2", "test3")))
    }

    @Test
    @Order(4)
    fun prune() {
        val user1 = userRepository.save(User(null, "test", "1234", "12", "test@test.com"))
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date(Date().time - 90 * 1000) //1.5 minutes in the past
            user = user1
        })
        var count = lastSeenService.getOnlineUserCount()
        Assertions.assertEquals(1, count)
        lastSeenService.prune()
        count = lastSeenService.getOnlineUserCount()
        Assertions.assertEquals(0, count)
    }

    @Test
    @Order(5)
    fun getBySession() {
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date()
            jSession = "1234"
        })

        val lastSeenEntity = lastSeenService.getBySession("1234")
        Assertions.assertNotNull(lastSeenEntity)
    }

    @Test
    @Order(6)
    fun getByUsername() {
        val user1 = userRepository.save(User(null, "test", "1234", "12", "test@test.com"))
        lastSeenRepository.save(LastSeenEntry().apply {
            lastSeen = Date(Date().time - 90 * 1000) //1.5 minutes in the past
            user = user1
        })
        val lastSeenEntity = lastSeenService.getByUsername("test")
        Assertions.assertNotNull(lastSeenEntity)
    }
}
