package com.homechange.api.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.Date

import java.util.Collections.emptyList

/**
 * Created by Nemanja on 5/13/17.

 * Helper service for creating and managing tokens
 */
object TokenAuthenticationService {

    internal val EXPIRATIONTIME = 1800000L // 30 minutes
    internal val SECRET = "ThisIsSecret"
    internal val TOKEN_PREFIX = "Bearer"
    internal val HEADER_STRING = "Authorization"

    /**
     * Creates new token and ads it to response

     * @param res
     * *
     * @param username
     */
    fun addAuthentication(res: HttpServletResponse, username: String) {
        val JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT)
    }

    /**
     * Checks if token is present in header in requests and parses it
     * Then returns ner Username and Password token or null if something went wrong

     * @param request
     * *
     * @return
     */
    fun getAuthentication(request: HttpServletRequest): Authentication? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {
            val user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
                    .subject

            return if (user != null) UsernamePasswordAuthenticationToken(user, null, emptyList<GrantedAuthority>()) else null
        }

        return null
    }
}
