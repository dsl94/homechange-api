package com.homechange.api.security.filter

import com.homechange.api.security.TokenAuthenticationService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import java.io.IOException

/**
 * Created by Nemanja on 5/13/17.

 * This filter will intercept all requests to validate the presence of the JWT–that is, the ones that are not issued
 * to / nor /users. This validation is done with the help of the TokenAuthenticationService class.
 */
class JWTAuthenticationFilter : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(servletRequest: ServletRequest,
                          servletResponse: ServletResponse,
                          filterChain: FilterChain) {

        val authentication = TokenAuthenticationService.getAuthentication(servletRequest as HttpServletRequest)

        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(servletRequest, servletResponse)
    }
}