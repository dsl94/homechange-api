package com.homechange.api.rest.dto.review

import com.homechange.api.model.Review
import javax.validation.constraints.NotNull

/**
 * Created by Nemanja on 6/16/17.
 *
 * DTO used when guest is responding to owners review
 */
class GuestReviewRequestDTO {

    @NotNull
    var ownerReviewId: Long? = null
    @NotNull
    var numberOfStars: Int? = null
    @NotNull
    var comment: String? = null

    constructor() {}

    constructor(ownerReviewId: Long, numberOfStars: Int, comment: String){
        this.ownerReviewId = ownerReviewId
        this.numberOfStars = numberOfStars
        this.comment = comment
    }

    /**
     * Method used for mapping request to review
     */
    fun mapToReview(): Review {
        val review = Review()
        review.numberOfStars = numberOfStars
        review.comment = comment

        return review
    }
}