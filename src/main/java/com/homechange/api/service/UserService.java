package com.homechange.api.service;

import com.homechange.api.error.UserException;
import com.homechange.api.rest.dto.user.UserRequestDTO;
import com.homechange.api.rest.dto.user.UserResponseDTO;

import java.util.List;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Service for User entity
 */
public interface UserService {

	/**
	 * Method that saves or updates user given user request object
	 * @param user User request object
	 * @return User response object
	 */
	UserResponseDTO save(UserRequestDTO user) throws UserException;

	/**
	 * Method that finds user by its id
	 * @param id User ID
	 * @return User response object
	 */
	UserResponseDTO get(Long id) throws UserException;

	/**
	 * Method that finds user by its username
	 * @param username Username
	 * @return User response object
	 */
	UserResponseDTO get(String username) throws UserException;

	/**
	 * Method that finds user by email
	 * @param email User's email
	 * @return User response object
	 */
	UserResponseDTO getByEmail(String email) throws UserException;

	/**
	 * Method that gets all users
	 * @return List of User response objects
	 */
	List<UserResponseDTO> getAll();
}
