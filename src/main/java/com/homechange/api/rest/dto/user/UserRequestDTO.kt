package com.homechange.api.rest.dto.user

import com.homechange.api.model.User

import javax.validation.constraints.NotNull

/**
 * Created by Nemanja on 5/14/17.

 * DTO that extends UserDTO
 * It is used for requests for creating user and contains password
 * TODO Maybe change name
 */
class UserRequestDTO : UserDTO {

    @NotNull
    var password: String? = null

    constructor() : super() {}

    constructor(username: String, firstName: String, lastName: String, email: String, country: String, city: String,
                password: String) : super(username, firstName, lastName, email, country, city) {
        this.password = password
    }

    /**
     * Method that maps DTO to User class to be used in repository
     * @return User class
     */
    fun mapToUser(): User {
        val user = User()
        user.username = this.username
        user.firstName = this.firstName
        user.lastName = this.lastName
        user.email = this.email
        user.country = this.country
        user.city = this.city
        user.address = this.address
        user.dateOfBirth = this.dateOfBirth
        user.phoneNumber = this.phoneNumber
        user.password = this.password

        return user
    }
}
