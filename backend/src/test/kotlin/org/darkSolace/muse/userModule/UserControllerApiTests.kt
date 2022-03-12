package org.darkSolace.muse.userModule

import org.darkSolace.muse.securityModule.model.JwtResponse
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.model.User
import org.darkSolace.muse.userModule.model.UserTag
import org.darkSolace.muse.userModule.service.UserRoleService
import org.darkSolace.muse.userModule.service.UserService
import org.darkSolace.muse.userModule.service.UserTagService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import java.util.*

class UserControllerApiTests : TestBase() {
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userRoleService: UserRoleService

    @Autowired
    private lateinit var userTagService: UserTagService

    @Test
    @Order(1)
    fun getUserById_publicUser() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))

        val url = generateUrl("/api/user/${user?.id}")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(user?.toPublicUser(), response.body)
    }

    @Test
    @Order(2)
    fun getUserById_ownUser() {
        var url = generateUrl("/api/auth/signup")
        val response = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals("User created successfully.", response.body)

        url = generateUrl("/api/auth/signin")
        val jwtResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${jwtResponse?.body?.token}")
        }

        url = generateUrl("/api/user/${jwtResponse?.body?.id}")
        val userResponse =
            restTemplate.exchange(url, HttpMethod.GET, HttpEntity<HttpHeaders>(headers), User::class.java)

        val user = userService.getById(jwtResponse?.body?.id ?: -1)
        Assertions.assertEquals(HttpStatus.OK, userResponse.statusCode)
        Assertions.assertEquals(user, userResponse.body)
    }

    @Test
    @Order(3)
    fun getUserById_unknownId() {
        val url = generateUrl("/api/user/99")
        val response = restTemplate.getForEntity(url, User::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNull(response.body)
    }

    @Test
    @Order(4)
    fun getAll() {
        val user = userService.createUser(User(username = "test", password = "123", email = "test@example.com"))
        val user2 = userService.createUser(User(username = "test2", password = "123", email = "test2@example.com"))
        val user3 = userService.createUser(User(username = "test3", password = "123", email = "test3@example.com"))
        val user4 = userService.createUser(User(username = "test4", password = "123", email = "test4@example.com"))

        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertTrue(response.body?.contains(user?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user2?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user3?.toPublicUser()) ?: false)
        Assertions.assertTrue(response.body?.contains(user4?.toPublicUser()) ?: false)
    }

    @Test
    @Order(5)
    fun getAll_Empty() {
        val url = generateUrl("/api/user/all")
        val response = restTemplate.getForEntity(url, Array<User>::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(0, response.body?.size ?: 99)
    }

    @Test
    @Order(6)
    fun suspendUser_success() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), HttpStatus::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.SUSPENDED)
    }

    @Test
    @Order(7)
    fun suspendUser_noRights() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate second user to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), String::class.java)

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.MEMBER)
    }

    @Test
    @Order(8)
    fun suspendUser_invalidId() {
        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to suspend user via api
        url = generateUrl("/api/user/suspend/4711")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    @Test
    @Order(9)
    fun confirmSuspension_success() {
        //generate user to suspend
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform the suspension
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //suspend user via api
        url = generateUrl("/api/user/suspend/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.POST, HttpEntity<HttpHeaders>(headers), ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.SUSPENDED)

        //try to sign in suspended user
        url = generateUrl("/api/auth/signin")
        val userSignInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "1234", "test@example.com"),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.TEMPORARY_REDIRECT, userSignInResponse.statusCode)
        val confirmationUrl = generateUrl("/api/user/${userSignInResponse.headers.location?.toString()}")

        val confirmation = restTemplate.exchange(confirmationUrl, HttpMethod.POST, null, ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.OK, confirmation.statusCode)
        user = userService.getById(user?.id ?: -1)
        Assertions.assertEquals(user?.role, Role.MEMBER)
    }

    @Test
    @Order(10)
    fun confirmSuspension_unknownCode() {
        val confirmationUrl = generateUrl("/api/user/suspend/confirm/testConfirmationCode")

        val confirmation = restTemplate.exchange(confirmationUrl, HttpMethod.POST, null, ResponseEntity::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, confirmation.statusCode)
    }

    @Test
    @Order(11)
    fun deleteUser_ownUserMember() {
        //generate user to delete
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            JwtResponse::class.java
        )

        val userId = signInResponse?.body?.id
        var user = userService.getById(userId ?: -1)
        Assertions.assertNotNull(user)

        //delete user via api
        url = generateUrl("/api/user/$userId")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(userId ?: -1)
        Assertions.assertNull(user)
    }

    @Test
    @Order(12)
    fun deleteUser_otherUserAdmin() {
        //generate user to delete
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform deletion
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //delete user via api
        url = generateUrl("/api/user/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertNull(user)
    }

    @Test
    @Order(13)
    fun deleteUser_otherUserModerator() {
        //generate user to delete
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform deletion
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.MODERATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to delete user via api
        url = generateUrl("/api/user/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertNotNull(user)
    }

    @Test
    @Order(14)
    fun deleteUser_otherUserMember() {
        //generate user to delete
        val signUpRequest = SignUpRequest("test", "1234", "test@example.com")
        var user = userService.createUserFromSignUpRequest(signUpRequest)

        //generate admin to perform deletion
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to delete user via api
        url = generateUrl("/api/user/${user?.id}")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)

        user = userService.getById(user?.id ?: -1)
        Assertions.assertNotNull(user)
    }

    @Test
    @Order(15)
    fun deleteUser_unknownUserAdmin() {
        //generate admin to perform deletion
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        userRoleService.changeRole(User(username = "test2", password = "", email = ""), Role.ADMINISTRATOR)

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to delete user via api
        url = generateUrl("/api/user/4711")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    @Order(16)
    fun deleteUser_unknownUserMember() {
        //generate admin to perform deletion
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

        url = generateUrl("/api/auth/signin")
        val signInResponse = restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            JwtResponse::class.java
        )

        //try to delete user via api
        url = generateUrl("/api/user/4711")
        val headers = HttpHeaders().apply {
            add("Authorization", "Bearer ${signInResponse?.body?.token}")
        }
        val response =
            restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity<HttpHeaders>(headers), Unit::class.java)

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
    }

    @Test
    @Order(17)
    fun addTag_ownUserMember() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )

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
    @Order(18)
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
    @Order(19)
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
    @Order(20)
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
    @Order(21)
    fun addTag_unknownUserAdmin() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

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
    @Order(22)
    fun addTag_unknownUserMember() {
        //generate user to perform add tag
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

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
    @Order(23)
    fun removeTag_ownUserMember() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test", "123", "test@example.com"),
            String::class.java
        )


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
    @Order(24)
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
    @Order(25)
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
    @Order(26)
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
    @Order(27)
    fun removeTag_unknownUserAdmin() {
        //generate user to add tag to
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

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
    @Order(28)
    fun removeTag_unknownUserMember() {
        //generate user to perform add tag
        //create user and sign in
        var url = generateUrl("/api/auth/signup")
        restTemplate.postForEntity(
            url,
            SignUpRequest("test2", "123", "test2@example.com"),
            String::class.java
        )

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
}
