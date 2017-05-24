package com.homechange.api.service.impl;

import com.homechange.api.error.ErrorCode;
import com.homechange.api.error.HomeException;
import com.homechange.api.model.Home;
import com.homechange.api.model.User;
import com.homechange.api.repository.HomeRepository;
import com.homechange.api.repository.UserRepository;
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO;
import com.homechange.api.rest.dto.home.HomeResponseDTO;
import com.homechange.api.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Created by Nemanja on 5/15/17.
 *
 * Implementation of Home Service
 */
@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private HomeRepository homeRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method for creating new home
	 *
	 * @param homeDTO Home DTO
	 * @return Created Home
	 */
	@Override
	public HomeResponseDTO create(CreateHomeRequestDTO homeDTO) throws HomeException {
		// First get logged in user's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		// Then try to find user
		User user = userRepository.findByUsernameIgnoreCase(username);
		if (user == null) {
			throw new HomeException("User for which you are trying to create home does not exist", ErrorCode.USER_NOT_FOUND);
		}
		// If user exist check if he already have home
		if (user.getHome() != null) {
			throw new HomeException("User already have home, edit it or delete it before adding another", ErrorCode.USER_ALREADY_HAVE_HOME);
		}
		// If everything passes, create home and return it
		Home forSave = homeDTO.mapToHome();
		forSave.setUser(user);
		Home result =  homeRepository.save(forSave);
		// Then we have to set home to user and save it
		user.setHome(result);
		userRepository.save(user);

		return new HomeResponseDTO(result);
	}

	/**
	 * method for updating existing home
	 *
	 * @param home Home
	 * @return updated Home
	 * TODO IMPLEMENT THIS -> This will be part of version 1.1
	 */
	@Override
	public Home update(Home home) {
		return null;
	}

	/**
	 * Method for searching home by user
	 *
	 * @param username Username
	 * @return Home
	 * TODO IMPLEMENT THIS
	 */
	@Override
	public HomeResponseDTO searchByUser(String username) throws HomeException {
		User user = userRepository.findByUsernameIgnoreCase(username);
		// Validate if user exist
		if (user == null) {
			throw new HomeException("User with that username not found", ErrorCode.USER_NOT_FOUND);
		}
		// Try to read home of the user
		Home home = user.getHome();
		// Check if exist
		if (home == null) {
			throw new HomeException("That user has no home", ErrorCode.HOME_NOT_FOUND);
		}

		// If everything is ok map to response and return
		return new HomeResponseDTO(home);
	}

	/**
	 * Method that searches for home by it's id
	 * @param id Id
	 * @return Home response
	 * @throws HomeException
	 */
	@Override
	public HomeResponseDTO searchById(Long id) throws HomeException {
		Home home = homeRepository.findOne(id);
		// Validate if home exist
		if (home == null) {
			throw new HomeException("Home with that id does not exist", ErrorCode.HOME_NOT_FOUND);
		}
		// If exist just map it and return
		return new HomeResponseDTO(home);
	}

	/**
	 * Method for deleting Home
	 *
	 * @param id Home id
	 */
	@Override
	public void delete(Long id) throws HomeException {
		Home home = homeRepository.findOne(id);
		if (home == null) {
			throw new HomeException("Home with that ID was not found", ErrorCode.HOME_NOT_FOUND);
		}
		User user = userRepository.findByUsernameIgnoreCase(home.getUser().getUsername());
		user.setHome(null);
		userRepository.save(user);
		homeRepository.delete(home);
	}
}
