package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.UserException
import com.homechange.api.model.User
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.user.UserRequestDTO
import com.homechange.api.rest.dto.user.UserResponseDTO
import com.homechange.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import java.util.ArrayList
import java.util.stream.Collectors

/**
 * Created by Nemanja on 5/14/17.

 * Implementation of user service
 */
@Service
class UserServiceImpl : UserService {

    @Autowired
    private val userRepository: UserRepository? = null


    /**
     * Method that saves or updates user given user request object

     * @param user User request object
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    override fun save(user: UserRequestDTO): UserResponseDTO {

        val encoder = BCryptPasswordEncoder()

        val forSave = user.mapToUser()
        // check for duplicate email
        if (userRepository?.findByEmailIgnoreCase(forSave.email) != null) {
            throw UserException("User with that email already exists", ErrorCode.EMAIL_ALREADY_IN_USE)
        }
        // check for duplicate username
        if (userRepository?.findByUsernameIgnoreCase(forSave.username) != null) {
            throw UserException("User with that username already exists", ErrorCode.USERNAME_ALREADY_IN_USE)
        }
        // If no exception is thrown, user is unique and we can save it
        // Before that we have to encode it's password
        val encodedPassword = encoder.encode(forSave.password)
        forSave.password = encodedPassword
        val result = userRepository?.save(forSave)

        return UserResponseDTO(result)
    }

    /**
     * Method that finds user by its id

     * @param id User ID
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    override fun get(id: Long?): UserResponseDTO {
        val result = userRepository?.findOne(id)
        if (result != null) {
            return UserResponseDTO(result)
        } else {
            throw UserException("User with that ID is not found", ErrorCode.USER_NOT_FOUND)
        }
    }

    /**
     * Method that finds user by its username

     * @param username Username
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    override fun get(username: String): UserResponseDTO {
        val result = userRepository?.findByUsernameIgnoreCase(username)
        if (result != null) {
            return UserResponseDTO(result)
        } else {
            throw UserException("User with that username is not found", ErrorCode.USER_NOT_FOUND)
        }
    }

    /**
     * Method that finds user by email

     * @param email User's email
     * *
     * @return User response object
     */
    @Throws(UserException::class)
    override fun getByEmail(email: String): UserResponseDTO {
        val result = userRepository?.findByEmailIgnoreCase(email)
        if (result != null) {
            return UserResponseDTO(result)
        } else {
            throw UserException("User with that email is not found", ErrorCode.USER_NOT_FOUND)
        }
    }

    /**
     * Method that gets all users

     * @return List of User response objects
     */
    override val all: List<UserResponseDTO>
        get() {
            val results = userRepository?.findAll()
            val list = ArrayList<UserResponseDTO>()
            for (result in results!!) {
                val userResponseDTO = UserResponseDTO(result)
                list.add(userResponseDTO)
            }
            return list
        }
}