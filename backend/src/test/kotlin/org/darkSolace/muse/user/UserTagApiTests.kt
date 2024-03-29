package org.darkSolace.muse.user

import org.darkSolace.muse.mail.service.MailService
import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.model.UserTag
import org.darkSolace.muse.user.repository.UserRepository
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.darkSolace.muse.user.service.UserTagService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class UserTagApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mailService: MailService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var userTagService: UserTagService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Order(1)
    fun addTag_ownUserMember() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")
        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val userId = signInResponse?.body?.id
        var user = userService.getById(userId ?: -1)
        Assertions.assertNotNull(user)

        //add ARTIST tag
        url = generateUrl("/api/user/$userId/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(userId ?: -1)
        Assertions.assertTrue(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(2)
    fun addTag_otherUserAdmin() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertTrue(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(3)
    fun addTag_otherUserModerator() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate moderator to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MODERATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertTrue(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(4)
    fun addTag_otherUserMember() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate other user to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertFalse(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(5)
    fun addTag_unknownUserAdmin() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/4711/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(6)
    fun addTag_unknownUserMember() {
        //generate user to perform add tag
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/4711/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.PUT, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(7)
    fun removeTag_ownUserMember() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test")

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val userId = signInResponse?.body?.id
        var user = userService.getById(userId ?: -1)
        Assertions.assertNotNull(user)
        if (user != null) {
            userTagService.addTagToUser(user, UserTag.ARTIST)
        }

        //add ARTIST tag
        url = generateUrl("/api/user/$userId/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(userId ?: -1)
        Assertions.assertFalse(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(8)
    fun removeTag_otherUserAdmin() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)
        if (user != null) {
            userTagService.addTagToUser(user, UserTag.ARTIST)
        }

        //generate admin to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertFalse(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(9)
    fun removeTag_otherUserModerator() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)
        if (user != null) {
            userTagService.addTagToUser(user, UserTag.ARTIST)
        }

        //generate moderator to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MODERATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertFalse(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(10)
    fun removeTag_otherUserMember() {
        //generate user to add tag to
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)
        if (user != null) {
            userTagService.addTagToUser(user, UserTag.ARTIST)
        }

        //generate other user to perform action
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/${user?.id}/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertTrue(user?.userTags?.contains(UserTag.ARTIST) ?: false)
    }

    @Test
    @Order(11)
    fun removeTag_unknownUserAdmin() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")
        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/4711/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(12)
    fun removeTag_unknownUserMember() {
        //generate user to perform add tag
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )
        mailService.markEMailAsValid("test2")


        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to add tag
        url = generateUrl("/api/user/4711/tag/ARTIST")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }


    @Test
    fun getAllWithUserTags() {
        // create test users
        userService.createUser(
            User(
                username = "testUser18", password = "123", email = "test18@example.com",
                userTags = mutableSetOf(UserTag.ARTIST)
            )
        )
        userService.createUser(
            User(
                username = "testUser19", password = "123", email = "test19@example.com",
                userTags = mutableSetOf(UserTag.ARTIST_INACTIVE)
            )
        )
        userService.createUser(
            User(
                username = "testUser20", password = "123", email = "test20@example.com",
                userTags = mutableSetOf(UserTag.BETA)
            )
        )
        userService.createUser(
            User(
                username = "testUser21", password = "123", email = "test21@example.com",
                userTags = mutableSetOf(UserTag.BETA_INACTIVE)
            )
        )
        userService.createUser(
            User(
                username = "testUser22", password = "123", email = "test22@example.com",
                userTags = mutableSetOf(UserTag.AUTHOR)
            )
        )
        userService.createUser(
            User(
                username = "testUser23", password = "123", email = "test23@example.com",
                userTags = mutableSetOf(UserTag.COMMENTER)
            )
        )
        userService.createUser(
            User(
                username = "testUser24", password = "123", email = "test24@example.com",
                userTags = mutableSetOf(UserTag.COMMENTER, UserTag.AUTHOR, UserTag.BETA_INACTIVE)
            )
        )
        userService.createUser(User(username = "testUser25", password = "123", email = "test25@example.com"))

        //collect values
        val countWithoutTags = userRepository.findAll().count { it.userTags.isEmpty() }
        val countBeta = userTagService.getAllWithUserTag(UserTag.BETA).count()
        val countBetaInactive = userTagService.getAllWithUserTag(UserTag.BETA_INACTIVE).count()
        val countArtist = userTagService.getAllWithUserTag(UserTag.ARTIST).count()
        val countArtistInactive = userTagService.getAllWithUserTag(UserTag.ARTIST_INACTIVE).count()
        val countCommenter = userTagService.getAllWithUserTag(UserTag.COMMENTER).count()
        val countAuthor = userTagService.getAllWithUserTag(UserTag.AUTHOR).count()

        //assertions
        Assertions.assertEquals(1, countWithoutTags)
        Assertions.assertEquals(1, countBeta)
        Assertions.assertEquals(2, countBetaInactive)
        Assertions.assertEquals(1, countArtist)
        Assertions.assertEquals(1, countArtistInactive)
        Assertions.assertEquals(2, countCommenter)
        Assertions.assertEquals(2, countAuthor)
    }
}

