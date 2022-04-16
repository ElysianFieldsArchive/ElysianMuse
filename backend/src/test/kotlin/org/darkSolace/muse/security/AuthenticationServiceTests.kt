package org.darkSolace.muse.security

import org.darkSolace.muse.security.model.JwtResponse
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.security.model.SignUpResponse
import org.darkSolace.muse.security.service.AuthenticationService
import org.darkSolace.muse.testUtil.TestBase
import org.darkSolace.muse.user.model.User
import org.darkSolace.muse.user.service.UserRoleService
import org.darkSolace.muse.user.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException

class AuthenticationServiceTests : TestBase() {
    @Autowired
    lateinit var authService: AuthenticationService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRoleService: UserRoleService

    @Test
    @Order(1)
    fun testSignUp() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)
    }

    @Test
    @Order(2)
    fun testSignUp_DuplicateUsername() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val secondSignupRequest = SignUpRequest("test", "123", "test2@example.com")
        val secondResponse = authService.signUpUser(secondSignupRequest)
        Assertions.assertEquals(SignUpResponse.USERNAME_EXISTS, secondResponse)
    }

    @Test
    @Order(3)
    fun testSignUp_DuplicateEMail() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        Assertions.assertEquals(SignUpResponse.OK, response)

        val secondSignupRequest = SignUpRequest("test2", "123", "test@example.com")
        val secondResponse = authService.signUpUser(secondSignupRequest)
        Assertions.assertEquals(SignUpResponse.EMAIL_EXISTS, secondResponse)
    }

    @Test
    @Order(4)
    fun testSignIn() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")
        Assertions.assertEquals(SignUpResponse.OK, response)

        val loginRequest = LoginRequest("test", "123")
        val jwtToken = authService.authenticate(loginRequest)
        Assertions.assertNotNull(jwtToken)
    }

    @Test
    @Order(5)
    fun testSignIn_WrongPassword() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")
        Assertions.assertEquals(SignUpResponse.OK, response)

        val loginRequest = LoginRequest("test", "1234")
        var jwtToken: JwtResponse? = null
        Assertions.assertThrows(BadCredentialsException::class.java) {
            jwtToken = authService.authenticate(loginRequest)
        }
        Assertions.assertNull(jwtToken)

    }

    @Test
    @Order(6)
    fun testSignIn_SuspendedUser() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")
        Assertions.assertEquals(SignUpResponse.OK, response)

        userRoleService.suspendUser(User(username = "test", password = "", email = "test@example.com"))
        val jwtResponse = authService.authenticate(LoginRequest("test", "123"))
        Assertions.assertEquals("SUSPENDED", jwtResponse?.role)
    }

    @Test
    @Order(7)
    fun testSignIn_UpdateLoginDate() {
        val signupRequest = SignUpRequest("test", "123", "test@example.com")
        val response = authService.signUpUser(signupRequest)
        userService.markEMailAsValid("test")
        Assertions.assertEquals(SignUpResponse.OK, response)

        var user = userService.userRepository.findByUsername("test")
        Assertions.assertNull(user?.lastLogInDate)

        val loginRequest = LoginRequest("test", "123")
        val jwtToken = authService.authenticate(loginRequest)
        Assertions.assertNotNull(jwtToken)

        user = userService.userRepository.findByUsername("test")
        Assertions.assertNotNull(user?.lastLogInDate)
    }
}
