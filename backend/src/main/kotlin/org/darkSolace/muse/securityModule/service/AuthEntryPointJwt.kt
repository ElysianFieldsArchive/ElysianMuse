package org.darkSolace.muse.securityModule.service

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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
