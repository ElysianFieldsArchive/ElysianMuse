package org.darkSolace.muse.security.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.darkSolace.muse.security.model.UserDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * Utility [Component] for tasks related to JWT
 */
@Component
class JwtUtils {
    @Value("\${muse.app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${muse.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

    private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

    /**
     * Generates a signed JWT token
     *
     * @param authentication [Authentication] containing the username the token is generated for
     * @return the JWT token
     */
    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal: UserDetails = authentication.principal as UserDetails
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(Keys.hmacShaKeyFor(jwtSecret?.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
    }

    /**
     * Extracts a username from a JWT token
     * @param token the JWT token
     * @return the username encoded in the token, or `null` if no username is found
     */
    fun getUserNameFromJwtToken(token: String?): String? {
        return Jwts.parserBuilder()
            .setSigningKey(Base64.getEncoder().encodeToString(jwtSecret?.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body.subject
    }

    /**
     * Validates a token for validity
     *
     * @param authToken the token to be validated
     * @return true if token is valid, else false
     */
    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(jwtSecret?.toByteArray()))
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: SecurityException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    /**
     * Extracts the JWT from a [HttpServletRequest]
     *
     * @param request the [HttpServletRequest] containing the authentication as a "Bearer"-Header
     * @return the extracted JWT or `null` if authentication is missing
     */
    fun parseJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (!authHeader.isNullOrBlank() && authHeader.startsWith("Bearer ")) {
            authHeader.removePrefix("Bearer ")
        } else
            null
    }
}
