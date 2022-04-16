package org.darkSolace.muse.security.service

import org.darkSolace.muse.security.exception.EMailNotValidatedException
import org.darkSolace.muse.security.model.*
import org.darkSolace.muse.user.model.Role
import org.darkSolace.muse.user.repository.UserRepository
import org.darkSolace.muse.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * [Service] to handle everything in regard to authentication.
 */
@Service
class AuthenticationService(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val userRepository: UserRepository,
    @Autowired val userService: UserService,
    @Autowired val jwtUtils: JwtUtils
) {
    /**
     * Tries to authenticate a user.
     *
     * TODO: Distinguish between wrong credentials and suspended users
     *
     * @param loginRequest [LoginRequest] containing username and password
     * @return [JwtResponse] containing the token or `null` if authentication failed
     * @throws BadCredentialsException
     * @throws EMailNotValidatedException
     */
    fun authenticate(loginRequest: LoginRequest): JwtResponse? {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtils.generateJwtToken(authentication)
        val userDetails = authentication.principal as UserDetails
        if (userDetails.user == null) throw BadCredentialsException("Invalid user")
        if (!userDetails.user.emailConfirmed) throw EMailNotValidatedException("Email is not validated")
        val role: Role = Role.valueOf(userDetails.authorities.first().authority)
        // update last login
        userService.updateLastLogin(userDetails.user)
        return JwtResponse(
            jwt,
            userDetails.user.id,
            userDetails.username,
            role.name
        )
    }

    /**
     * Creates a user with information provided via a [SignUpRequest]
     *
     * @param signUpRequest [SignUpRequest] containing username, password and email
     * @return [SignUpResponse] containing the signup status
     */
    fun signUpUser(signUpRequest: SignUpRequest): SignUpResponse {
        return when {
            userRepository.existsByUsernameIgnoreCase(signUpRequest.username) ->
                SignUpResponse.USERNAME_EXISTS
            userRepository.existsByEmailIgnoreCase(signUpRequest.email) ->
                SignUpResponse.EMAIL_EXISTS
            else -> {
                userService.createUserFromSignUpRequest(signUpRequest)
                SignUpResponse.OK
            }
        }
    }
}
