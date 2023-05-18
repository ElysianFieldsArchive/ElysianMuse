package org.darkSolace.muse.user

import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.service.UserService
import org.darkSolace.muse.user.service.UserTagService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired

class UserTagServiceTests : TestBase() {
    @Autowired
    lateinit var userTagService: UserTagService

    @Autowired
    lateinit var userService: UserService

    @Test
    fun addTagToUser() {
        //create test user
        val user = userService.createUser(
            User(
                username = "testUser14",
                password = "123456",
                email = "test14@example.com"
            )
        ) ?: fail("user is null")

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
                password = "123456",
                email = "test914@example.com"
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
    fun addTagToUserUnknownUser() {
        //try to remove tag from unknown user
        val result = userTagService.addTagToUser(
            User(username = "unknown", password = "123456", email = "test@example.com"),
            UserTag.BETA
        )
        Assertions.assertNull(result)
    }

    @Test
    fun removeTagFromUser() {
        //create test user with tags BETA and ARTIST
        val user = userService.createUser(
            User(
                username = "testUser15",
                password = "123456",
                email = "test15@example.com",
                userTags = mutableSetOf(UserTag.BETA, UserTag.ARTIST)
            )
        ) ?: fail("user is null")

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
                password = "123456",
                email = "test915@example.com",
                userTags = mutableSetOf(UserTag.BETA, UserTag.ARTIST)
            )
        ) ?: fail("user is null")
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

    @Test
    fun removeTagFromUserUnknownUser() {
        //try to remove tag from unknown user
        val result = userTagService.removeTagFromUser(
            User(username = "unknown", password = "123456", email = "test@example.com"),
            UserTag.BETA
        )
        Assertions.assertNull(result)
    }
}
