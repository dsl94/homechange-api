package com.homechange.api.service

import com.homechange.api.error.HomeException
import com.homechange.api.model.Home
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO
import com.homechange.api.rest.dto.home.HomeResponseDTO

/**
 * Created by Nemanja on 5/15/17.

 * Service interface for Home entity
 */
interface HomeService {

    /**
     * Method for creating new home
     * @param homeDTO Home DTO
     * *
     * @return Created Home DTO
     */
    @Throws(HomeException::class)
    fun create(homeDTO: CreateHomeRequestDTO): HomeResponseDTO

    /**
     * method for updating existing home
     * @param home Home
     * *
     * @return updated Home
     */
    fun update(home: Home): Home

    /**
     * Method for searching home by user
     * @param username Username
     * *
     * @return Home
     */
    @Throws(HomeException::class)
    fun searchByUser(username: String): HomeResponseDTO

    /**
     * Method that searches for home by it's id
     * @param id Id
     * *
     * @return Home response
     * *
     * @throws HomeException
     */
    @Throws(HomeException::class)
    fun searchById(id: Long?): HomeResponseDTO

    /**
     * Method for deleting Home
     * @param id Home id
     */
    @Throws(HomeException::class)
    fun delete(id: Long?)
}
