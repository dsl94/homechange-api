package com.homechange.api.rest.dto.review

import com.homechange.api.model.Review

import javax.validation.constraints.NotNull

/**
 * Created by Nemanja on 6/15/17.
 *
 * DTO for
 */
class OwnerReviewRequestDTO {

    @NotNull
    var numberOfStars: Int? = null
    @NotNull
    var offerId: Long? = null
    @NotNull
    var userToReviewUsername: String? = null
    @NotNull
    var comment: String? = null

    constructor() {}

    constructor(numberOfStars: Int, offerId: Long, userToReviewUsername: String, comment: String){
        this.numberOfStars = numberOfStars
        this.offerId = offerId
        this.userToReviewUsername = userToReviewUsername
        this.comment = comment
    }

    /**
     * Function for mapping DTO class to review
     */
    fun mapToReview(): Review {
        val review = Review()
        review.numberOfStars = numberOfStars
        review.comment = comment

        return review
    }
}