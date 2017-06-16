package com.homechange.api.rest.dto.user

import com.homechange.api.rest.dto.review.UsersReviewsDTO

/**
 * Created by Nemanja on 6/16/17.
 */
class PublicUserProfileDTO {

    var userId: Long? = null
    var username: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var country: String? = null
    var city: String? = null
    var reviews: UsersReviewsDTO? = null

    constructor() {}
    constructor(userId: Long, username: String, firstName: String, lastName: String, country: String, city: String, reviews: UsersReviewsDTO) {
        this.userId = userId
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.country = country
        this.city = city
        this.reviews = reviews
    }


}