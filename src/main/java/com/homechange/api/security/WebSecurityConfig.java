package com.homechange.api.security;

import com.homechange.api.security.filter.JWTAuthenticationFilter;
import com.homechange.api.security.filter.JWTLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Nemanja on 5/13/17.
 *
 * This class is used to configure which resources will be available
 * to everyone and which can only be accessed with token
 *
 * Here we are configuring access to / for everyone and /login for everyone
 * and we are filtering /login requests with JWT Filter (see that class)
 * and we are filtering all other requests with JWTAuthenticationFilter (see thath class)
 * to check if there is JWT in header
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/api/register").permitAll()
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.anyRequest().authenticated()
				.and()
				// We filter the api/login requests
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// And filter other requests to check the presence of JWT in header
				.addFilterBefore(new JWTAuthenticationFilter(),
						UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * We are creating default user with ADMIN role
	 * TODO: REMOVE THIS AND MAKE SOMETHING THAT WORKS
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Create a default account
		auth.inMemoryAuthentication()
				.withUser("admin")
				.password("password")
				.roles("ADMIN");
	}
}
