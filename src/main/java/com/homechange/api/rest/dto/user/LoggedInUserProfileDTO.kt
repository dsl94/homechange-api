package com.homechange.api.rest.dto.user

import com.homechange.api.rest.dto.review.UsersReviewsDTO

/**
 * Created by Nemanja on 6/16/17.
 */
class LoggedInUserProfileDTO {

    var userData: UserResponseDTO? = null
    var reviews: UsersReviewsDTO? = null

    constructor() {}

    constructor(userData: UserResponseDTO, reviews: UsersReviewsDTO) {
        this.userData = userData
        this.reviews = reviews
    }
}