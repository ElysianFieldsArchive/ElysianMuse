package org.darkSolace.muse.lastSeen.service

import org.darkSolace.muse.security.service.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filter to create or update [org.darkSolace.muse.lastSeen.model.LastSeenEntry] for each request
 *
 * @see [OncePerRequestFilter]
 */
@Component
class LastSeenFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var lastSeenService: LastSeenService

    /**
     * Creates or updates [org.darkSolace.muse.lastSeen.model.LastSeenEntry]s during the request
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jSession = request.session.id
        val token = jwtUtils.parseJwt(request)
        val authenticatedUser = if (token != null && jwtUtils.validateJwtToken(token)) {
            jwtUtils.getUserNameFromJwtToken(token)
        } else null

        lastSeenService.updateLastSeen(authenticatedUser, jSession)

        filterChain.doFilter(request, response)
    }
}
