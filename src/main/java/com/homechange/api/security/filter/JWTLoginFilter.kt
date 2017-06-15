package com.homechange.api.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.homechange.api.security.AccountCredentials
import com.homechange.api.security.TokenAuthenticationService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.Collections

/**
 * Created by Nemanja on 5/13/17.

 * This class will intercept POST requests on the /login path and attempt to authenticate the user.
 * When the user is successfully authenticated, it will return a JWT in the Authorization header of the response

 * During the authentication attempt, which is dealt by the attemptAuthentication method,
 * we retrieve the username and password from the request.
 * After they are retrieved, we use the AuthenticationManager to verify that these details match with an existing user.
 * If it does, we enter the successfulAuthentication method.
 * In this method we fetch the name from the authenticated user, and pass it on to TokenAuthenticationService,
 * which will then add a JWT to the response.
 */
class JWTLoginFilter(url: String, authManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(AntPathRequestMatcher(url)) {

    init {
        authenticationManager = authManager
    }


    @Throws(AuthenticationException::class, IOException::class, ServletException::class)
    override fun attemptAuthentication(httpServletRequest: HttpServletRequest,
                                       httpServletResponse: HttpServletResponse): Authentication {

        // Reading username and password from request and mapping to class that will represent them
        val creds = ObjectMapper()
                .readValue(httpServletRequest.inputStream, AccountCredentials::class.java)

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(creds.username, creds.password, emptyList<GrantedAuthority>())
        )
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            req: HttpServletRequest,
            res: HttpServletResponse, chain: FilterChain?,
            auth: Authentication) {
        TokenAuthenticationService
                .addAuthentication(res, auth.name)
    }
}
