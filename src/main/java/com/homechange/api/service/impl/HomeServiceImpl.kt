package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.HomeException
import com.homechange.api.model.Home
import com.homechange.api.model.User
import com.homechange.api.repository.HomeRepository
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.home.CreateHomeRequestDTO
import com.homechange.api.rest.dto.home.HomeResponseDTO
import com.homechange.api.service.HomeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * Created by Nemanja on 5/15/17.

 * Implementation of Home Service
 */
@Service
class HomeServiceImpl : HomeService {

    @Autowired
    private val homeRepository: HomeRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    /**
     * Method for creating new home

     * @param homeDTO Home DTO
     * *
     * @return Created Home
     */
    @Throws(HomeException::class)
    override fun create(homeDTO: CreateHomeRequestDTO): HomeResponseDTO {
        // First get logged in user's username
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.principal as String
        // Then try to find user
        val user = userRepository?.findByUsernameIgnoreCase(username) ?: throw HomeException("User for which you are trying to create home does not exist", ErrorCode.USER_NOT_FOUND)
        // If user exist check if he already have home
        if (user.home != null) {
            throw HomeException("User already have home, edit it or delete it before adding another", ErrorCode.USER_ALREADY_HAVE_HOME)
        }
        // If everything passes, create home and return it
        val forSave = homeDTO.mapToHome()
        forSave.user = user
        val result = homeRepository?.save(forSave)
        // Then we have to set home to user and save it
        user.home = result
        userRepository.save(user)

        return HomeResponseDTO(result)
    }

    /**
     * method for updating existing home

     * @param home Home
     * *
     * @return updated Home
     * * TODO IMPLEMENT THIS -> This will be part of version 1.1
     */
    override fun update(home: Home): Home {
        TODO()
    }

    /**
     * Method for searching home by user

     * @param username Username
     * *
     * @return Home
     * * TODO IMPLEMENT THIS
     */
    @Throws(HomeException::class)
    override fun searchByUser(username: String): HomeResponseDTO {
        val (_, _, _, _, _, _, _, _, _, _, _, home1) = userRepository?.findByUsernameIgnoreCase(username) ?: throw HomeException("User with that username not found", ErrorCode.USER_NOT_FOUND)
        // Validate if user exist
        // Try to read home of the user
        val home = home1 ?: throw HomeException("That user has no home", ErrorCode.HOME_NOT_FOUND)
        // Check if exist

        // If everything is ok map to response and return
        return HomeResponseDTO(home)
    }

    /**
     * Method that searches for home by it's id
     * @param id Id
     * *
     * @return Home response
     * *
     * @throws HomeException
     */
    @Throws(HomeException::class)
    override fun searchById(id: Long?): HomeResponseDTO {
        val home = homeRepository?.findOne(id) ?: throw HomeException("Home with that id does not exist", ErrorCode.HOME_NOT_FOUND)
        // Validate if home exist
        // If exist just map it and return
        return HomeResponseDTO(home)
    }

    /**
     * Method for deleting Home

     * @param id Home id
     */
    @Throws(HomeException::class)
    override fun delete(id: Long?) {
        val home = homeRepository?.findOne(id) ?: throw HomeException("Home with that ID was not found", ErrorCode.HOME_NOT_FOUND)
        val user = userRepository?.findByUsernameIgnoreCase(home.user?.username)
        user?.home = null
        userRepository?.save(user)
        homeRepository.delete(home)
    }
}
