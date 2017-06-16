package com.homechange.api.rest.dto.review

import com.homechange.api.model.Review

/**
 * Created by Nemanja on 6/15/17.
 *
 */
class ReviewResponseDTO {
    var reviewId: Long? = null
    var numberOfStars: Int? = null
    var reviewedUser: String? = null

    constructor() {}

    constructor(review: Review?) {
        this.reviewId = review?.id
        this.numberOfStars = review?.numberOfStars
        this.reviewedUser = review?.reviewedUser?.username
    }
}