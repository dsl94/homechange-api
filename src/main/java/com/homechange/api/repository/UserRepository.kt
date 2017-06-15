package com.homechange.api.repository

import com.homechange.api.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Nemanja on 5/14/17.
 */
interface UserRepository : JpaRepository<User, Long> {

    /**
     * Method that finds user by username
     * @param username Username
     * *
     * @return User
     */
    fun findByUsernameIgnoreCase(username: String?): User?

    /**
     * Method that finds user by Email
     * @param email Email
     * *
     * @return User
     */
    fun findByEmailIgnoreCase(email: String?): User?
}
