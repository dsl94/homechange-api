package com.homechange.api.rest.dto.review

/**
 * Created by Nemanja on 6/16/17.
 *
 * DTO Used for user profile
 */
class UsersReviewsDTO {

    var numberOfReviews: Int? = null
    var averageNumberOfStars: Double? = null
    var reviews: List<ReviewResponseDTO>? = null

    constructor() {}

    constructor(numberOfReviews: Int, averageNumberOfStars: Double, reviews: List<ReviewResponseDTO>) {
        this.numberOfReviews = numberOfReviews
        this.averageNumberOfStars = averageNumberOfStars
        this.reviews = reviews
    }
}