package com.homechange.api.rest.dto.user

import com.homechange.api.model.User

/**
 * Created by Nemanja on 5/14/17.

 * DTO Used in responses, contains user id
 */
class UserResponseDTO : UserDTO {

    var userId: Long? = null

    constructor() : super() {}

    /**
     * Constructor to be used for mapping repository object to response
     * @param user User
     */
    constructor(user: User?) : super(user?.username, user?.firstName, user?.lastName, user?.email, user?.country,
            user?.city, user?.address, user?.dateOfBirth, user?.phoneNumber) {
        this.userId = user?.id
    }
}
