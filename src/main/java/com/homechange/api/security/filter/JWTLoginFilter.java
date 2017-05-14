package com.homechange.api.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homechange.api.security.AccountCredentials;
import com.homechange.api.security.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * Created by Nemanja on 5/13/17.
 *
 * This class will intercept POST requests on the /login path and attempt to authenticate the user.
 * When the user is successfully authenticated, it will return a JWT in the Authorization header of the response
 *
 * During the authentication attempt, which is dealt by the attemptAuthentication method,
 * we retrieve the username and password from the request.
 * After they are retrieved, we use the AuthenticationManager to verify that these details match with an existing user.
 * If it does, we enter the successfulAuthentication method.
 * In this method we fetch the name from the authenticated user, and pass it on to TokenAuthenticationService,
 * which will then add a JWT to the response.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	public JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}


	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
												HttpServletResponse httpServletResponse)
			throws AuthenticationException, IOException, ServletException {

		// Reading username and password from request and mapping to class that will represent them
		AccountCredentials creds = new ObjectMapper()
				.readValue(httpServletRequest.getInputStream(), AccountCredentials.class);

		return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), Collections.emptyList())
		);
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest req,
			HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService
				.addAuthentication(res, auth.getName());
	}
}
