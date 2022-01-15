package org.darkSolace.muse.securityModule.model

/**
 * [SignUpRequest] model containing username, password and email to create a new user account
 * @See [org.darkSolace.muse.userModule.service.UserService.createUser]
 */
data class SignUpRequest(val username: String, val password: String, val email: String)
