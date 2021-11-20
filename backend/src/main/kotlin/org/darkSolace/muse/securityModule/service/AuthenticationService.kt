package org.darkSolace.muse.securityModule.service

import org.darkSolace.muse.securityModule.model.*
import org.darkSolace.muse.userModule.model.Role
import org.darkSolace.muse.userModule.repository.UserRepository
import org.darkSolace.muse.userModule.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationService {
    private val logger = LoggerFactory.getLogger(this::class.simpleName)

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtUtils: JwtUtils

    fun authenticate(loginRequest: LoginRequest): JwtResponse? {
        try {
            val authentication: Authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
            )
            SecurityContextHolder.getContext().authentication = authentication
            val jwt = jwtUtils.generateJwtToken(authentication)
            val userDetails = authentication.principal as UserDetails
            val role: Role = Role.valueOf(userDetails.authorities.first().authority)
            return JwtResponse(
                jwt,
                userDetails.user.id,
                userDetails.username,
                userDetails.getEmail(),
                role.name
            )
        } catch (e: BadCredentialsException) {
            logger.error("Cannot set user authentication: {}", e)
            return null
        }
    }

    fun signUpUser(signUpRequest: SignupRequest): SignUpResponse {
        return when {
            userRepository.existsByUsername(signUpRequest.username) ->
                SignUpResponse.USERNAME_EXISTS
            userRepository.existsByEmail(signUpRequest.email) ->
                SignUpResponse.EMAIL_EXISTS
            else -> {
                userService.createUserFromSignUpRequest(signUpRequest)
                SignUpResponse.OK
            }
        }
    }
}
