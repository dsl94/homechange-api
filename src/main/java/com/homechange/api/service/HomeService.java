package com.homechange.api.service;

import com.homechange.api.error.HomeException;
import com.homechange.api.model.Home;
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO;
import com.homechange.api.rest.dto.home.HomeResponseDTO;

/**
 * Created by Nemanja on 5/15/17.
 *
 * Service interface for Home entity
 */
public interface HomeService {

	/**
	 * Method for creating new home
	 * @param homeDTO Home DTO
	 * @return Created Home DTO
	 */
	HomeResponseDTO create(CreateHomeRequestDTO homeDTO) throws HomeException;

	/**
	 * method for updating existing home
	 * @param home Home
	 * @return updated Home
	 */
	Home update(Home home);

	/**
	 * Method for searching home by user
	 * @param username Username
	 * @return Home
	 */
	HomeResponseDTO searchByUser(String username) throws HomeException;

	/**
	 * Method that searches for home by it's id
	 * @param id Id
	 * @return Home response
	 * @throws HomeException
	 */
	HomeResponseDTO searchById(Long id) throws HomeException;

	/**
	 * Method for deleting Home
	 * @param id Home id
	 */
	 void delete(Long id) throws HomeException;
}
