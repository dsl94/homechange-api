package com.homechange.api.service.impl;

import com.homechange.api.error.ErrorCode;
import com.homechange.api.error.UserException;
import com.homechange.api.model.User;
import com.homechange.api.repository.UserRepository;
import com.homechange.api.rest.dto.user.UserRequestDTO;
import com.homechange.api.rest.dto.user.UserResponseDTO;
import com.homechange.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Implementation of user service
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method that saves or updates user given user request object
	 *
	 * @param user User request object
	 * @return User response object
	 */
	@Override
	public UserResponseDTO save(UserRequestDTO user) throws UserException {
		User forSave = user.mapToUser();
		User result = userRepository.save(forSave);
		return new UserResponseDTO(result);
	}

	/**
	 * Method that finds user by its id
	 *
	 * @param id User ID
	 * @return User response object
	 */
	@Override
	public UserResponseDTO get(Long id) throws UserException {
		User result = userRepository.findOne(id);
		if (result != null) {
			return new UserResponseDTO(result);
		} else {
			throw new UserException("User with that ID is not found", ErrorCode.USER_NOT_FOUND);
		}
	}

	/**
	 * Method that finds user by its username
	 *
	 * @param username Username
	 * @return User response object
	 */
	@Override
	public UserResponseDTO get(String username) throws UserException {
		User result = userRepository.findByUsername(username);
		if (result != null) {
			return new UserResponseDTO(result);
		} else {
			throw new UserException("User with that username is not found", ErrorCode.USER_NOT_FOUND);
		}
	}

	/**
	 * Method that finds user by email
	 *
	 * @param email User's email
	 * @return User response object
	 */
	@Override
	public UserResponseDTO getByEmail(String email) throws UserException {
		User result = userRepository.findByEmail(email);
		if (result != null) {
			return new UserResponseDTO(result);
		} else {
			throw new UserException("User with that email is not found", ErrorCode.USER_NOT_FOUND);
		}
	}

	/**
	 * Method that gets all users
	 *
	 * @return List of User response objects
	 */
	@Override
	public List<UserResponseDTO> getAll() {
		List<User> results = userRepository.findAll();
		return results.stream().map(UserResponseDTO::new).collect(Collectors.toList());
	}
}
