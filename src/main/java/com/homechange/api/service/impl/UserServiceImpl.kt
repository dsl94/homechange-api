package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.UserException
import com.homechange.api.model.User
import com.homechange.api.repository.ReviewRepository
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.review.ReviewResponseDTO
import com.homechange.api.rest.dto.review.UsersReviewsDTO
import com.homechange.api.rest.dto.user.LoggedInUserProfileDTO
import com.homechange.api.rest.dto.user.PublicUserProfileDTO
import com.homechange.api.rest.dto.user.UserRequestDTO
import com.homechange.api.rest.dto.user.UserResponseDTO
import com.homechange.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
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

    @Autowired
    private val reviewRepository: ReviewRepository? = null


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

    /**
     * Method that return profile of logged in user
     */
    @Throws(UserException::class)
    override fun loggedInUserProfile(): LoggedInUserProfileDTO {
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.principal as String
        val userResponse = this[loggedInUsername]
        val reviews = usersReviewsDTO(loggedInUsername)

        val response = LoggedInUserProfileDTO(userResponse, reviews)

        return response
    }

    /**
     * Method that return profile of any user
     */
    @Throws(UserException::class)
    override fun publicInUserProfile(username: String): PublicUserProfileDTO {
        // First we are getting user and validating it
        val user = userRepository?.findByUsernameIgnoreCase(username) ?: throw UserException("User with that username does not exist", ErrorCode.USER_NOT_FOUND)
        val reviews = usersReviewsDTO(user.username!!)

        val response = PublicUserProfileDTO(user.id!!, user.username!!,user.firstName!!, user.lastName!!, user.country!!, user.city!!, reviews)

        return response
    }

    /**
     * Helper function that finds users reviews
     */
    private fun usersReviewsDTO(loggedInUsername: String): UsersReviewsDTO {
        val plainReviews = reviewRepository?.findByReviewedUser(userRepository?.findByUsernameIgnoreCase(loggedInUsername)!!)
        val reviews = UsersReviewsDTO()
        val reviewsResponses = mutableListOf<ReviewResponseDTO>()
        var sumOfStars = 0.0
        for (review in plainReviews!!) {
            val r = ReviewResponseDTO(review)
            sumOfStars = sumOfStars.plus(r.numberOfStars ?: 0)
            reviewsResponses.add(r)
        }
        reviews.numberOfReviews = plainReviews.size
        reviews.averageNumberOfStars = sumOfStars.div(plainReviews.size)
        reviews.reviews = reviewsResponses
        return reviews
    }
}
