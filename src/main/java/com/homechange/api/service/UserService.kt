package com.homechange.api.service

import com.homechange.api.error.UserException
import com.homechange.api.rest.dto.user.*

/**
 * Created by Nemanja on 5/14/17.

 * Service for User entity
 */
interface UserService {

    /**
     * Method that saves or updates user given user request object
     * @param user User request object
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    fun save(user: UserRequestDTO): UserResponseDTO

    /**
     * Method that finds user by its id
     * @param id User ID
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    operator fun get(id: Long?): UserResponseDTO

    /**
     * Method that finds user by its username
     * @param username Username
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    operator fun get(username: String): UserResponseDTO

    /**
     * Method that finds user by email
     * @param email User's email
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    fun getByEmail(email: String): UserResponseDTO

    /**
     * Method that gets all users
     * @return List of User response objects
     */
    val all: List<UserResponseDTO>

    /**
     * Method that return profile of logged in user
     */
    @Throws(UserException::class)
    fun loggedInUserProfile(): LoggedInUserProfileDTO

    /**
     * Method that return profile of any user
     */
    @Throws(UserException::class)
    fun publicInUserProfile(username: String): PublicUserProfileDTO

    /**
     * Method used for saving profile picture of the user
     * If user have profile picture, the old one will be deleted
     * and new one will be uploaded
     */
    @Throws(UserException::class)
    fun uploadProfilePicture(profilePictureDTO: UserPictureUploadDTO): UserPictureUploadResponseDTO
}
