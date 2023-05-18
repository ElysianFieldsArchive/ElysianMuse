package org.darkSolace.muse.user

import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.*
import org.darkSolace.muse.user.repository.UserRepository
import org.darkSolace.muse.user.service.AvatarService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.shaded.org.hamcrest.MatcherAssert
import org.testcontainers.shaded.org.hamcrest.Matchers
import java.util.*

class UserServiceTests : TestBase() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var avatarService: AvatarService

    // RegExHelpers
    val defaultUserSettings =
        "UserSettings\\(id = \\d+, selectedLayout = null, emailVisible = false, birthdayVisible = false, " +
                "realNameVisible = false, maxRating = PARENTAL_GUIDANCE_13, shareButtonsVisible = true, " +
                "showEntireStories = false, selectedFontFamily = SANS, storyBannersVisible = true, " +
                "selectedFontSize = MEDIUM, hideContributions = false\\)"

    @Test
    fun createUserTest() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())
    }

    @Test
    fun createUserDuplicateUsername() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())

        val duplicateUser =
            userService.createUser(User(username = "testUser", password = "123456", email = "test2@example.com"))
        Assertions.assertNull(duplicateUser)
    }


    @Test
    fun createUserDuplicateUsernameDiffCase() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())

        val duplicateUser =
            userService.createUser(User(username = "TESTuser", password = "123456", email = "test2@example.com"))
        Assertions.assertNull(duplicateUser)
    }


    @Test
    fun createUserDuplicateEMail() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())

        val duplicateUser =
            userService.createUser(User(username = "testUser2", password = "123456", email = "test@example.com"))
        Assertions.assertNull(duplicateUser)
    }


    @Test
    fun createUserDuplicateEMailDiffCase() {
        val userCount = userRepository.count()
        userService.createUser(User(username = "testUser", password = "123456", email = "test@example.com"))
        Assertions.assertEquals(userCount + 1, userRepository.count())

        val duplicateUser =
            userService.createUser(User(username = "testUser2", password = "123456", email = "TEST@EXample.com"))
        Assertions.assertNull(duplicateUser)
    }

    @Test
    fun createFullUserTest() {
        // create test user with specific values and save to db
        val userCount = userRepository.count()
        val user = User(
            id = null,
            username = "testUser2",
            password = "123456",
            email = "test2@example.com",
            realName = "Thomas Test",
            signUpDate = Date(),
            lastLogInDate = null,
            bio = "Short Bio",
            birthday = GregorianCalendar(1980, 0, 1).time,
            validatedAuthor = false,
            onProbation = false,
            userSettings = UserSettings(),
            userTags = mutableSetOf(UserTag.BETA),
            avatar = Avatar(null, byteArrayOf(0x00, 0x01, 0x02, 0x03)),
            role = Role.MEMBER
        )
        userRepository.save(user)

        // check if user is created, retrieve it and check content of retrieved user
        Assertions.assertEquals(userCount + 1, userRepository.count())
        MatcherAssert.assertThat(
            userService.getById(user.id!!).toString(),
            Matchers.matchesPattern(
                """User\(id = \d+ , username = testUser2 , email = test\d@example\.com , """ +
                        """realName = Thomas Test , signUpDate = 202\d-\d{2}-\d{2} \d{2}:\d{2}:\d{2}\.\d{0,3} , """ +
                        """lastLogInDate = null , bio = Short Bio , birthday = 1980-01-01 , """ +
                        """validatedAuthor = false , onProbation = false , userSettings = $defaultUserSettings , """ +
                        """userTags = \[BETA] , avatar = Avatar\(id = \d+\) , role = MEMBER , """ +
                        """emailConfirmed = false , emailConfirmationCode = null \)"""
                            .toPattern()
            )
        )
    }

    @Test
    fun changeAvatar() {
        //create test user
        val user =
            userService.createUser(User(username = "testUser5", password = "123456", email = "test5@example.com"))
                ?: fail("user is null")
        Assertions.assertNull(user.avatar)

        //set an avatar and save avatarId
        avatarService.changeAvatar(user, Avatar(null, byteArrayOf(0, 1, 2, 3)))
        val avatarId = user.avatar?.id
        Assertions.assertNotNull(user.avatar?.id)
        Assertions.assertArrayEquals(byteArrayOf(0, 1, 2, 3), user.avatar?.avatarBlob)

        // change the avatar, check if blob changed but id stayed the same
        avatarService.changeAvatar(user, Avatar(null, byteArrayOf(3, 4, 5, 6)))
        Assertions.assertArrayEquals(byteArrayOf(3, 4, 5, 6), user.avatar?.avatarBlob)
        Assertions.assertEquals(avatarId, user.avatar?.id)
    }

    @Test
    fun changeAvatarByUsername() {
        //create test user
        var user =
            userService.createUser(User(username = "testUser95", password = "123456", email = "test95@example.com"))
                ?: fail("user is null")
        Assertions.assertNull(user.avatar)
        val userByUsername = User(username = "testUser95", password = "", email = "")

        //set an avatar and save avatarId
        user = avatarService.changeAvatar(userByUsername, Avatar(null, byteArrayOf(0, 1, 2, 3)))
            ?: fail("User was null")
        val avatarId = user.avatar?.id
        Assertions.assertNotNull(user.avatar?.id)
        Assertions.assertArrayEquals(byteArrayOf(0, 1, 2, 3), user.avatar?.avatarBlob)

        // change the avatar, check if blob changed but id stayed the same
        user = avatarService.changeAvatar(userByUsername, Avatar(null, byteArrayOf(3, 4, 5, 6)))
            ?: fail("User was null")
        Assertions.assertArrayEquals(byteArrayOf(3, 4, 5, 6), user.avatar?.avatarBlob)
        Assertions.assertEquals(avatarId, user.avatar?.id)
    }

    @Test
    fun changeAvatarUnknownUser() {
        //create unknown user - not persisted
        val unknownUser = User(username = "notKnown", password = "", email = "")

        //assert return null
        val user = avatarService.changeAvatar(unknownUser, Avatar(avatarBlob = byteArrayOf(1, 2, 3, 4)))
        Assertions.assertNull(user)
    }

    @Test
    fun getById() {
        // create test user and save to db
        val user =
            userService.createUser(User(username = "testUser6", password = "123456", email = "test6@example.com"))
                ?: fail("user is null")
        val id = user.id
        // retrieve same user from db
        val otherUser = userService.getById(id ?: -1L)
        // check if they are the same
        Assertions.assertEquals(user, otherUser)
    }

    @Test
    fun getAll() {
        // get before count
        val count = userRepository.count()
        // create test user
        val user =
            userService.createUser(User(username = "testUser7", password = "123456", email = "test7@example.com"))
        val user2 =
            userService.createUser(User(username = "testUser8", password = "123456", email = "test8@example.com"))
        val user3 =
            userService.createUser(User(username = "testUser9", password = "123456", email = "test9@example.com"))

        // get all users
        val all = userService.getAll()
        //should be three more than before
        Assertions.assertEquals(count.toInt() + 3, all.count())
        //check if all created test users are there
        Assertions.assertTrue(all.contains(user))
        Assertions.assertTrue(all.contains(user2))
        Assertions.assertTrue(all.contains(user3))
    }

    @Test
    fun getAllWithRole() {
        // create test user
        userService.createUser(User(username = "testUser10", password = "123456", email = "test10@example.com"))
        userService.createUser(
            User(
                username = "testUser11", password = "123456", email = "test11@example.com",
                role = Role.ADMINISTRATOR
            )
        )
        userService.createUser(
            User(
                username = "testUser12", password = "123456", email = "test12@example.com",
                role = Role.MODERATOR
            )
        )
        userService.createUser(
            User(
                username = "testUser13", password = "123456", email = "test13@example.com",
                role = Role.SUSPENDED
            )
        )

        //get all users by role
        val count = userRepository.count()
        val members = userService.getAllWithRole(Role.MEMBER)
        val admins = userService.getAllWithRole(Role.ADMINISTRATOR)
        val mods = userService.getAllWithRole(Role.MODERATOR)
        val suspended = userService.getAllWithRole(Role.SUSPENDED)

        //check if everything adds up and all users per group have the specified role
        Assertions.assertEquals(count.toInt(), members.count() + admins.count() + mods.count() + suspended.count())
        Assertions.assertTrue(members.all { it.role == Role.MEMBER })
        Assertions.assertTrue(mods.all { it.role == Role.MODERATOR })
        Assertions.assertTrue(admins.all { it.role == Role.ADMINISTRATOR })
        Assertions.assertTrue(suspended.all { it.role == Role.SUSPENDED })
    }

    @Test
    fun deleteUser() {
        // create test user
        val user =
            userService.createUser(User(username = "testUser16", password = "123456", email = "test16@example.com"))
                ?: fail("user is null")
        val count = userRepository.count()
        Assertions.assertTrue(userService.getAll().contains(user))

        userService.deleteUser(user)

        Assertions.assertEquals(count - 1, userRepository.count())
        Assertions.assertFalse(userService.getAll().contains(user))
    }

    @Test
    fun deleteUserById() {
        // create test user
        val user =
            userService.createUser(User(username = "testUser16", password = "123456", email = "test16@example.com"))
                ?: fail("user is null")
        val count = userRepository.count()
        Assertions.assertTrue(userService.getAll().contains(user))

        userService.deleteUser(user.id ?: -1)

        Assertions.assertEquals(count - 1, userRepository.count())
        Assertions.assertFalse(userService.getAll().contains(user))
    }

    @Test
    fun deleteUserByUserName() {
        // create test user
        val user =
            userService.createUser(User(username = "testUser916", password = "123456", email = "test916@example.com"))
        val count = userRepository.count()
        Assertions.assertTrue(userService.getAll().contains(user))
        val userByUsername = User(username = "testUser916", password = "", email = "")

        userService.deleteUser(userByUsername)

        Assertions.assertEquals(count - 1, userRepository.count())
        Assertions.assertFalse(userService.getAll().contains(user))
    }

    @Test
    fun deleteUnknownUser() {
        //create unknown user - not persisted
        val unknownUser = User(username = "notKnown", password = "", email = "")

        //assert return Unit
        val user = userService.deleteUser(unknownUser)
        Assertions.assertInstanceOf(Unit::class.java, user)
    }

    @Test
    fun updateUserSettings() {
        // create test user
        val user =
            userService.createUser(User(username = "testUser17", password = "123456", email = "test17@example.com"))
                ?: fail("user is null")
        val oldUserSettings = user.userSettings.copy()

        // set new user settings and check changes as well as check if previous settings are still default
        userService.updateUserSettings(user, UserSettings(emailVisible = true, realNameVisible = true))
        Assertions.assertNotEquals(oldUserSettings, user.userSettings)
        Assertions.assertTrue(user.userSettings.emailVisible)
        Assertions.assertTrue(user.userSettings.realNameVisible)
        Assertions.assertFalse(user.userSettings.birthdayVisible)
    }

    @Test
    fun updateUserSettingsByUsername() {
        // create test user
        var user =
            userService.createUser(User(username = "testUser917", password = "123456", email = "test17@example.com"))
                ?: fail("user is null")
        val oldUserSettings = user.userSettings.copy()
        val userByUsername = User(username = "testUser917", password = "", email = "")

        // set new user settings and check changes as well as check if previous settings are still default
        user = userService.updateUserSettings(userByUsername, UserSettings(emailVisible = true, realNameVisible = true))
            ?: fail("User was null")
        Assertions.assertNotEquals(oldUserSettings, user.userSettings)
        Assertions.assertTrue(user.userSettings.emailVisible)
        Assertions.assertTrue(user.userSettings.realNameVisible)
        Assertions.assertFalse(user.userSettings.birthdayVisible)
    }

    @Test
    fun updateUserSettingsForUnknownUser() {
        //create unknown user - not persisted
        val unknownUser = User(username = "notKnown", password = "", email = "")

        //assert return null
        val user = userService.updateUserSettings(unknownUser, UserSettings())
        Assertions.assertNull(user)
    }
}
