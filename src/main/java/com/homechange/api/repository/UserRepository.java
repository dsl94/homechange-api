package com.homechange.api.repository;

import com.homechange.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nemanja on 5/14/17.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Method that finds user by username
	 * @param username Username
	 * @return User
	 */
	User findByUsernameIgnoreCase(String username);

	/**
	 * Method that finds user by Email
	 * @param email Email
	 * @return User
	 */
	User findByEmailIgnoreCase(String email);
}
