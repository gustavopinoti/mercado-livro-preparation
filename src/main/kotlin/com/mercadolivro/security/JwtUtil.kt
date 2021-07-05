package com.mercadolivro.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    fun generateToken(username: String?): String {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + expiration!!))
                .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray())
                .compact()
    }

    fun isValidToken(token: String): Boolean {
        val claims = getClaims(token)
        val username = claims.subject
        val expirationDate = claims.expiration
        val now = Date(System.currentTimeMillis())
        if (username == null || expirationDate == null || now.after(expirationDate)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {
        return try {
            Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body
        } catch (e: Exception) {
            throw Error("Can not get claims from token") //TODO criar um erro para esse cenario
        }
    }

    fun getUsername(token: String): String? {
        val claims = getClaims(token)
        return claims.subject
    }
}