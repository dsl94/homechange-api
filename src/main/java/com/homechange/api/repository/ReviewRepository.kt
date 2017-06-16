package com.homechange.api.repository

import com.homechange.api.model.Review
import com.homechange.api.model.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Created by Nemanja on 6/15/17.
 *
 * Repository used for reviews
 */
interface ReviewRepository : JpaRepository<Review, Long>{

    /**
     * Method that finds all reviews where user have been reviewed
     */
    fun findByReviewedUser(reviewedUser: User) : List<Review>
}