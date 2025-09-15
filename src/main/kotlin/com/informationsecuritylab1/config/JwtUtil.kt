package com.informationsecuritylab1.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${app.jwt.secret}") private val jwtSecret: String,
    @Value("\${app.jwt.expiration-ms}") private val expirationMs: Long
) {
    private val key: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    fun generateToken(username: String): String {
        val now = Date()
        val exp = Date(now.time + expirationMs)
        return Jwts.builder()
            .subject(username)
            .issuedAt(now)
            .expiration(exp)
            .signWith(key)
            .compact()
    }

    fun validateAndGetUsername(token: String): String {
        val claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)

        return claims.payload.subject
    }
}
