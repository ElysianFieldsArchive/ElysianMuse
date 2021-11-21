package org.darkSolace.muse.securityModule.controller

import org.darkSolace.muse.securityModule.model.LoginRequest
import org.darkSolace.muse.securityModule.model.SignUpRequest
import org.darkSolace.muse.securityModule.model.SignUpResponse
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
class AuthController(@Autowired val authenticationService: AuthenticationService) {
    /**
     * Checks a transmitted [LoginRequest] for a valid username/password pair. Listens on /api/auth/signin.
     *
     * @sample `curl localhost:8080/api/auth/signin`
     * @param loginRequest a [LoginRequest] containing username and password
     * @return a [org.darkSolace.muse.securityModule.model.JwtResponse] containing
     * a token or HTTP 401 is username or password are invalid
     */
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*>? {
        val token = authenticationService.authenticate(loginRequest)
        return if (token == null) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password wrong!")
        } else {
            ResponseEntity.ok().body(token)
        }
    }

    /**
     * Checks a transmitted [SignUpRequest] and creates a user if possible. Listens on /api/auth/signup.
     *
     * @sample `curl localhost:8080/api/auth/signup`
     * @param signUpRequest a [SignUpRequest] containing username, password and email address
     * @return HTTP-Status 200 OK if user was created successfully or 400 BAD REQUEST if an error occurred
     */
    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<*> {
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
