package org.darkSolace.muse.security.controller

import org.darkSolace.muse.security.exception.EMailNotValidatedException
import org.darkSolace.muse.security.model.LoginRequest
import org.darkSolace.muse.security.model.SignUpRequest
import org.darkSolace.muse.security.model.SignUpResponse
import org.darkSolace.muse.security.service.AuthenticationService
import org.darkSolace.muse.user.service.SuspensionService
import org.darkSolace.muse.user.service.UserRoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/auth")
class AuthController(
    @Autowired val authenticationService: AuthenticationService,
    @Autowired val userRoleService: UserRoleService,
    @Autowired val suspensionService: SuspensionService
) {
    private final val usernamePasswordWrongMsg = "Unknown username or wrong password!"
    private final val emailNotValidatedMsg = "Email is not validated!"

    /**
     * Checks a transmitted [LoginRequest] for a valid username/password pair. Listens on /api/auth/signin.
     *
     * @sample `curl -X POST -H "Content-Type: application/json" -d '{ "username": "test", "password": "123" }'
     *          localhost:8000/api/auth/signin`
     * @param loginRequest a [LoginRequest] containing username and password
     * @return a [org.darkSolace.muse.security.model.JwtResponse] containing
     * a token, HTTP 401 is username or password are invalid, or HTTP 301 is user is suspended
     */
    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {
        return try {
            val token = authenticationService.authenticate(loginRequest)
            if (token == null) {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(usernamePasswordWrongMsg)
            } else {
                //valid sign in, but check if user is suspended
                val authentication =
                    SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken?
                if (authentication != null &&
                    authentication.authorities.contains(SimpleGrantedAuthority("SUSPENDED"))
                ) {
                    val confirmationCode = suspensionService.suspensionCodeForUsername(loginRequest.username)
                    ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                        .header("Location", "/suspend/confirm/$confirmationCode")
                        .body("User is suspended")
                } else {
                    ResponseEntity.ok().body(token)
                }
            }
        } catch (_: InternalAuthenticationServiceException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(usernamePasswordWrongMsg)
        } catch (_: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(usernamePasswordWrongMsg)
        } catch (_: EMailNotValidatedException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emailNotValidatedMsg)
        }
    }

    /**
     * Checks a transmitted [SignUpRequest] and creates a user if possible. Listens on /api/auth/signup.
     *
     * @sample `curl -X POST -H "Content-Type: application/json" -d
     *          '{ "username": "test", "password": "123", "email": "test@example.com" }'
     *          localhost:8000/api/auth/signup`
     * @param signUpRequest a [SignUpRequest] containing username, password and email address
     * @return HTTP-Status 200 OK if user was created successfully or 400 BAD REQUEST if an error occurred
     */
    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<String> {
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