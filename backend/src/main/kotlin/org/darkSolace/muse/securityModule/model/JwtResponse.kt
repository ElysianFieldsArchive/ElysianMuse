package org.darkSolace.muse.securityModule.model

/**
 * [JwtResponse] class containing the token, user id, username and email.
 * Used as after a successful login to transmit all required data.
 *
 * @see [org.darkSolace.muse.securityModule.controller.AuthController.authenticateUser]
 */
class JwtResponse(val token: String?, val id: Long?, val username: String, val role: String)
