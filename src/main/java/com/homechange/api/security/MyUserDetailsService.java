package com.homechange.api.security;

import com.homechange.api.model.User;
import com.homechange.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Nemanja on 5/15/17.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameIgnoreCase(s);
		if (user == null) {
			throw new UsernameNotFoundException(s);
		}
		return new MyUserPrincipal(user);
	}
}
