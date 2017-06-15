package com.homechange.api.repository

import com.homechange.api.model.Home
import com.homechange.api.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Nemanja on 5/15/17.

 * Repository for Home entity
 */
interface HomeRepository : JpaRepository<Home, Long> {

    /**
     * Method that finds home by User
     * @param user User
     * *
     * @return Home
     */
    fun findByUser(user: User): Home?
}
