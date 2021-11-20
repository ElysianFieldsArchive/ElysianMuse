package org.darkSolace.muse.securityModule.service

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.darkSolace.muse.securityModule.model.UserDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtUtils {
    private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)

    @Value("\${muse.app.jwtSecret}")
    private val jwtSecret: String? = null

    @Value("\${muse.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

    fun generateJwtToken(authentication: Authentication): String? {
        val userPrincipal: UserDetails = authentication.principal as UserDetails
        return Jwts.builder().setSubject(userPrincipal.username).setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(Keys.hmacShaKeyFor(jwtSecret?.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserNameFromJwtToken(token: String?): String? {
        return Jwts.parserBuilder()
            .setSigningKey(Base64.getEncoder().encodeToString(jwtSecret?.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body.subject
    }

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
}
