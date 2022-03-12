package org.darkSolace.muse.security.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Filter to check the provided authorization for validity
 *
 * @see [OncePerRequestFilter]
 */
@Component
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
//        try {
        val jwt = jwtUtils.parseJwt(request)
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
        /*} catch (e: UsernameNotFoundException) {
            logger.error("Cannot set user authentication: {}", e)
        }*/

        filterChain.doFilter(request, response)
    }
}
