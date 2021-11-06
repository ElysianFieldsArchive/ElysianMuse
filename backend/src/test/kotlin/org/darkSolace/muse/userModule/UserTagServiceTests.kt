package org.darkSolace.muse.userModule

import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTags
import org.darkSolace.muse.userModule.service.UserService
import org.darkSolace.muse.userModule.service.UserTagService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
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
        userTagService.addTagToUser(user, UserTags.BETA)
        // check if tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        userTagService.addTagToUser(user, UserTags.BETA)
        // check if tag isn't applied twice
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        userTagService.addTagToUser(user, UserTags.BETA_INACTIVE)
        // check if active tag is removed if inactive tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA_INACTIVE))
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        userTagService.addTagToUser(user, UserTags.BETA)
        // check if inactive tag is removed if active tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA_INACTIVE))
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
        var user = userTagService.addTagToUser(userByUsername, UserTags.BETA)
        // check if tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        user = userTagService.addTagToUser(userByUsername, UserTags.BETA)
        // check if tag isn't applied twice
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        user = userTagService.addTagToUser(userByUsername, UserTags.BETA_INACTIVE)
        // check if active tag is removed if inactive tag is applied
        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA_INACTIVE))
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        user = userTagService.addTagToUser(userByUsername, UserTags.BETA)
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

        userTagService.removeTagFromUser(user, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))

        //try to remove nonexistent tag
        userTagService.removeTagFromUser(user, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))
    }

    @Test
    fun removeTagFromUserByUsername() {
        var user = userService.createUser(
            User(
                username = "testUser915",
                password = "123",
                email = "test915@example.com",
                userTags = mutableSetOf(UserTags.BETA, UserTags.ARTIST)
            )
        )
        val userByUsername = User(username = "testUser915", password = "", email = "")

        Assertions.assertEquals(2, user.userTags.count())
        Assertions.assertTrue(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))

        user = userTagService.removeTagFromUser(userByUsername, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))

        //try to remove nonexistent tag
        user = userTagService.removeTagFromUser(userByUsername, UserTags.BETA)

        Assertions.assertEquals(1, user.userTags.count())
        Assertions.assertFalse(user.userTags.contains(UserTags.BETA))
        Assertions.assertTrue(user.userTags.contains(UserTags.ARTIST))
    }
}
