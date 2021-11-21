package org.darkSolace.muse.securityModule.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Filter to check the provided authorization for validity
 *
 * @see [OncePerRequestFilter]
 */
class AuthTokenFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUtils: JwtUtils
    @Autowired
    lateinit var userDetailsService: UserDetailsService

    /**
     * Checks and stores the authentication for a request
     *
     * @see [OncePerRequestFilter.doFilterInternal]
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = parseJwt(request)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                val username = jwtUtils.getUserNameFromJwtToken(jwt)

                val userDetails = userDetailsService.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: UsernameNotFoundException) {
            logger.error("Cannot set user authentication: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    /**
     * Extracts the JWT from a [HttpServletRequest]
     *
     * @param request the [HttpServletRequest] containing the authentication as a "Bearer"-Header
     * @return the extracted JWT or `null` if authentication is missing
     */
    private fun parseJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (!authHeader.isNullOrBlank() && authHeader.startsWith("Bearer ")) {
            authHeader.removePrefix("Bearer ")
        } else
            null
    }
}
