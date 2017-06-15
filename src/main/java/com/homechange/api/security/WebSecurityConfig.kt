package com.homechange.api.security

import com.homechange.api.security.filter.JWTAuthenticationFilter
import com.homechange.api.security.filter.JWTLoginFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Created by Nemanja on 5/13/17.

 * This class is used to configure which resources will be available
 * to everyone and which can only be accessed with token

 * Here we are configuring access to / for everyone and /login for everyone
 * and we are filtering /login requests with JWT Filter (see that class)
 * and we are filtering all other requests with JWTAuthenticationFilter (see that class)
 * to check if there is JWT in header
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val userDetailsService: MyUserDetailsService? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/offerdetails").permitAll()
                .antMatchers("/api/findoffer").permitAll()
                .antMatchers("/api/homedetails").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilterBefore(JWTLoginFilter("/api/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(JWTAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter::class.java)
    }

    /**
     * We are creating default user with ADMIN role
     * @param auth
     * *
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        val encoder = BCryptPasswordEncoder()
        auth!!.userDetailsService<MyUserDetailsService>(userDetailsService).passwordEncoder(encoder)
    }
}
