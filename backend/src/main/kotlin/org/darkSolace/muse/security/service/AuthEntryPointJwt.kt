package org.darkSolace.muse.security.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

/**
 * [Component] to handle unauthorised access to an endpoint
 *
 * @see [AuthenticationEntryPoint]
 */
@Component
class AuthEntryPointJwt : AuthenticationEntryPoint {

    /**
     * Handles the request when an endpoint is accessed without authorisation.
     * Leads to an 401 UNAUTHORIZED HTTP Status code.
     */
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized")
    }
}
