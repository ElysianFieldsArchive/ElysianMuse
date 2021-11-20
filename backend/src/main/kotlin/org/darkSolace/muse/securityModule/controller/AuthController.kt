package org.darkSolace.muse.securityModule.controller

import org.darkSolace.muse.securityModule.model.LoginRequest
import org.darkSolace.muse.securityModule.model.SignUpResponse
import org.darkSolace.muse.securityModule.model.SignupRequest
import org.darkSolace.muse.securityModule.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/auth")
class AuthController {
    @Autowired
    lateinit var authenticationService: AuthenticationService

    @PostMapping("/signin")
    fun authenticateUser(/*@Valid*/ @RequestBody loginRequest: LoginRequest): ResponseEntity<*>? {
        val token = authenticationService.authenticate(loginRequest)
        return if (token == null) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password wrong!")
        } else {
            ResponseEntity.ok().body("User logged in successfully")
        }
    }

    @PostMapping("/signup")
    fun registerUser(/*@Valid*/ @RequestBody signUpRequest: SignupRequest): ResponseEntity<String> {
        return when (authenticationService.signUpUser(signUpRequest)) {
            SignUpResponse.USERNAME_EXISTS -> {
                ResponseEntity
                    .badRequest()
                    .body("Error: Username is already in use!")
            }
            SignUpResponse.EMAIL_EXISTS -> {
                ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!")
            }
            SignUpResponse.OK -> ResponseEntity.ok("User created successfully.")
        }
    }
}
