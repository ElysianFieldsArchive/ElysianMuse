package org.darkSolace.muse.userModule

import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTag
import org.darkSolace.muse.userModule.service.UserService
import org.darkSolace.muse.userModule.service.UserTagService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class UserTagServiceTests {
    @Autowired
    lateinit var userTagService: UserTagService

    @Autowired
    lateinit var userService: UserService

    companion object {
        @Container
        private val postgresqlContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer<Nothing>("postgres:14.0-alpine").apply {
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

    @Test
    fun addTagToUser() {
        //create test user
        val user = userService.createUser(
            User(
                username = "testUser14",
                password = "123",
                email = "test14@example.com",
                userTags = mutableSetOf()
            )
        )

        // check if tag is applied
        userTagService.addTagToUser(user, UserTag.BETA)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))

        // check if tag isn't applied twice
        userTagService.addTagToUser(user, UserTag.BETA)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))

        // check if active tag is removed if inactive tag is applied
        userTagService.addTagToUser(user, UserTag.BETA_INACTIVE)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA_INACTIVE))
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))

        // check if inactive tag is removed if active tag is applied
        userTagService.addTagToUser(user, UserTag.BETA)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA_INACTIVE))
    }

    @Test
    fun addTagToUserByUsername() {
        userService.createUser(
            User(
                username = "testUser914",
                password = "123",
                email = "test914@example.com",
                userTags = mutableSetOf()
            )
        )
        val userByUsername = User(username = "testUser914", password = "", email = "")

        // check if tag is applied
        var user = userTagService.addTagToUser(userByUsername, UserTag.BETA) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))

        // check if tag isn't applied twice
        user = userTagService.addTagToUser(userByUsername, UserTag.BETA) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))

        // check if active tag is removed if inactive tag is applied
        user = userTagService.addTagToUser(userByUsername, UserTag.BETA_INACTIVE) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA_INACTIVE))

        // check if inactive tag is removed if active tag is applied
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))
        user = userTagService.addTagToUser(userByUsername, UserTag.BETA) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA_INACTIVE))
    }

    @Test
    fun removeTagFromUser() {
        //create test user with tags BETA and ARTIST
        val user = userService.createUser(
            User(
                username = "testUser15",
                password = "123",
                email = "test15@example.com",
                userTags = mutableSetOf(UserTag.BETA, UserTag.ARTIST)
            )
        )

        //check if user is created correctly
        Assertions.assertEquals(2, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))

        //try to remove existing tag
        userTagService.removeTagFromUser(user, UserTag.BETA)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))

        //try to remove nonexistent tag
        userTagService.removeTagFromUser(user, UserTag.BETA)
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))
    }

    @Test
    fun removeTagFromUserByUsername() {
        //create test user with user tags BETA and ARTIST
        var user = userService.createUser(
            User(
                username = "testUser915",
                password = "123",
                email = "test915@example.com",
                userTags = mutableSetOf(UserTag.BETA, UserTag.ARTIST)
            )
        )
        val userByUsername = User(username = "testUser915", password = "", email = "")

        //check if user is created correctly
        Assertions.assertEquals(2, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))

        //try to remove existing tag
        user = userTagService.removeTagFromUser(userByUsername, UserTag.BETA) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))

        //try to remove nonexistent tag
        user = userTagService.removeTagFromUser(userByUsername, UserTag.BETA) ?: fail("User was null")
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTag.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTag.ARTIST))
    }
}
