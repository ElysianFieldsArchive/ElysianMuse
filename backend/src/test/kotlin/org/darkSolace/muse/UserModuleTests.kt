package org.darkSolace.muse

import org.darkSolace.muse.userModule.model.*
import org.darkSolace.muse.userModule.repository.UserRepository
import org.darkSolace.muse.userModule.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.hamcrest.MatcherAssert
import org.testcontainers.shaded.org.hamcrest.Matchers
import java.util.*

@SpringBootTest
@Testcontainers
class UserModuleTests {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    companion object {
        @Container
        private val postgresqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:14.0").apply {
            withDatabaseName("foo")
            withUsername("foo")
            withPassword("secret")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", postgresqlContainer::getPassword)
            registry.add("spring.datasource.username", postgresqlContainer::getUsername)
        }
    }

    // RegExHelpers
    val defaultUserSettings =
        "UserSettings\\(id = \\d+ , selectedLayout = null , emailVisible = false , birthdayVisible = false , " +
                "realNameVisible = false , maxRating = PARENTAL_GUIDANCE_13 , shareButtonsVisible = true , " +
                "showEntireStories = false , selectedFontFamily = SANS , storyBannersVisible = true , " +
                "selectedFontSize = MEDIUM \\)"
    val regExDay = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)"
    val regExMonth = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)"

    @Test
    fun createUserTest() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())
    }

    @Test
    fun createFullUserTest() {
        val userCount = userRepository.count()
        val user = User(
            id = null,
            username = "testUser2",
            password = "123",
            email = "test2@example.com",
            realName = "Thomas Test",
            signUpDate = Date(),
            lastLogInDate = null,
            bio = "Short Bio",
            birthday = GregorianCalendar(1980, 0, 1).time,
            validatedAuthor = false,
            onProbation = false,
            userSettings = UserSettings(),
            userTags = mutableSetOf(UserTags.BETA),
            avatar = Avatar(null, byteArrayOf(0x00, 0x01, 0x02, 0x03)),
            role = Role.MEMBER
        )
        userRepository.save(user)

        Assertions.assertEquals(userCount + 1, userRepository.count())
        MatcherAssert.assertThat(
            user.toString(),
            Matchers.matchesPattern(
                """User\(id = \d+ , username = testUser2 , password = 123 , """ +
                        """email = test\d@example.com , realName = Thomas Test , """ +
                        """signUpDate = $regExDay $regExMonth \d+ \d+:\d+:\d+ """ +
                        """CET 202\d , lastLogInDate = null , bio = Short Bio , """ +
                        """birthday = $regExDay $regExMonth 01 00:00:00 CET 1980 , """ +
                        """validatedAuthor = false , onProbation = false , userSettings = $defaultUserSettings , """ +
                        """userTags = \[BETA] , avatar = Avatar\(id = \d+ \) , roles = MEMBER \)""".toPattern()
            )
        )
    }

    @Test
    fun suspendUser() {
        val user = userService.createUser(User(username = "testUser3", password = "123", email = "test3@example.com"))
        Assertions.assertNotEquals(Role.SUSPENDED, user.role)
        userService.suspendUser(user)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeRole() {
        val user = userService.createUser(User(username = "testUser4", password = "123", email = "test4@example.com"))
        Assertions.assertEquals(Role.MEMBER, user.role)
        userService.changeRole(user, Role.MODERATOR)
        Assertions.assertEquals(Role.MODERATOR, user.role)
        userService.changeRole(user, Role.ADMINISTRATOR)
        Assertions.assertEquals(Role.ADMINISTRATOR, user.role)
        userService.changeRole(user, Role.SUSPENDED)
        Assertions.assertEquals(Role.SUSPENDED, user.role)
    }

    @Test
    fun changeAvatar() {
        val user = userService.createUser(User(username = "testUser5", password = "123", email = "test5@example.com"))
        Assertions.assertNull(user.avatar)
        userService.changeAvatar(user, Avatar(null, byteArrayOf(0, 1, 2, 3)))
        val avatarId = user.avatar?.id
        Assertions.assertNotNull(user.avatar?.id)
        Assertions.assertArrayEquals(byteArrayOf(0, 1, 2, 3), user.avatar?.avatarBlob)
        userService.changeAvatar(user, Avatar(null, byteArrayOf(3, 4, 5, 6)))
        Assertions.assertArrayEquals(byteArrayOf(3, 4, 5, 6), user.avatar?.avatarBlob)
        Assertions.assertEquals(avatarId, user.avatar?.id)
    }

    @Test
    fun getById() {
        val user = userService.createUser(User(username = "testUser6", password = "123", email = "test6@example.com"))
        val id = user.id
        val otherUser = userService.getById(id ?: -1L)
        Assertions.assertEquals(user, otherUser)
    }

    @Test
    fun getAll() {
        val count = userRepository.count()
        val user = userService.createUser(User(username = "testUser7", password = "123", email = "test7@example.com"))
        val user2 = userService.createUser(User(username = "testUser8", password = "123", email = "test8@example.com"))
        val user3 = userService.createUser(User(username = "testUser9", password = "123", email = "test9@example.com"))
        Assertions.assertEquals(count + 3, userRepository.count())
        val all = userService.getAll()
        Assertions.assertTrue(all.contains(user))
        Assertions.assertTrue(all.contains(user2))
        Assertions.assertTrue(all.contains(user3))
    }

    @Test
    fun getAllWithRole() {
        userService.createUser(User(username = "testUser10", password = "123", email = "test10@example.com"))
        val members = userService.getAllWithRole(Role.MEMBER)
        userService.createUser(
            User(
                username = "testUser11",
                password = "123",
                email = "test11@example.com",
                role = Role.ADMINISTRATOR
            )
        )
        val admins = userService.getAllWithRole(Role.ADMINISTRATOR)
        userService.createUser(
            User(
                username = "testUser12",
                password = "123",
                email = "test12@example.com",
                role = Role.MODERATOR
            )
        )
        val mods = userService.getAllWithRole(Role.MODERATOR)
        userService.createUser(
            User(
                username = "testUser13",
                password = "123",
                email = "test13@example.com",
                role = Role.SUSPENDED
            )
        )
        val suspended = userService.getAllWithRole(Role.SUSPENDED)
        val count = userRepository.count()

        Assertions.assertEquals(count.toInt(), members.count() + admins.count() + mods.count() + suspended.count())
        Assertions.assertTrue(members.all { it.role == Role.MEMBER })
        Assertions.assertTrue(mods.all { it.role == Role.MODERATOR })
        Assertions.assertTrue(admins.all { it.role == Role.ADMINISTRATOR })
        Assertions.assertTrue(suspended.all { it.role == Role.SUSPENDED })
    }

    @Test
    fun addTagToUser() {
        val user = userService.createUser(
            User(
                username = "testUser14",
                password = "123",
                email = "test14@example.com",
                userTags = mutableSetOf()
            )
        )
        userService.addTagToUser(user, UserTags.BETA)
        // check if tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        userService.addTagToUser(user, UserTags.BETA)
        // check if tag isn't applied twice
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        userService.addTagToUser(user, UserTags.BETA_INACTIVE)
        // check if active tag is removed if inactive tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA_INACTIVE))
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        userService.addTagToUser(user, UserTags.BETA)
        // check if inactive tag is removed if active tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA_INACTIVE))
    }

    @Test
    fun removeTagFromUser() {
        val user = userService.createUser(
            User(
                username = "testUser15",
                password = "123",
                email = "test15@example.com",
                userTags = mutableSetOf(UserTags.BETA, UserTags.ARTIST)
            )
        )

        Assertions.assertEquals(2, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))

        userService.removeTagFromUser(user, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))

        //try to remove nonexistent tag
        userService.removeTagFromUser(user, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))
    }

    @Test
    fun deleteUser() {
        val user = userService.createUser(User(username = "testUser16", password = "123", email = "test16@example.com"))
        val count = userRepository.count()
        Assertions.assertTrue(userService.getAll().contains(user))

        userService.deleteUser(user)

        Assertions.assertEquals(count - 1, userRepository.count())
        Assertions.assertFalse(userService.getAll().contains(user))
    }

    @Test
    fun updateUserSettings() {
        val user = userService.createUser(User(username = "testUser17", password = "123", email = "test17@example.com"))
        val oldUserSettings = user.userSettings.copy()

        userService.updateUserSettings(user, UserSettings(emailVisible = true, realNameVisible = true))

        Assertions.assertNotEquals(oldUserSettings, user.userSettings)
        Assertions.assertTrue(user.userSettings.emailVisible)
        Assertions.assertTrue(user.userSettings.realNameVisible)
        Assertions.assertFalse(user.userSettings.birthdayVisible)
    }
}
