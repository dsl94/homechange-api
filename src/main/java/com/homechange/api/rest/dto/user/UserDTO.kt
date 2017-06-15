package com.homechange.api.rest.dto.user

import javax.validation.constraints.NotNull
import java.util.Date

/**
 * Created by Nemanja on 5/14/17.

 * Base User DTO class, contains everything except password and id
 * Required fields for creating are marked with not null anotation
 */
open class UserDTO {

    @NotNull
    var username: String? = null
    @NotNull
    var firstName: String? = null
    @NotNull
    var lastName: String? = null
    @NotNull
    var email: String? = null
    @NotNull
    var country: String? = null
    @NotNull
    var city: String? = null
    var address: String? = null
    var dateOfBirth: Date? = null
    var phoneNumber: String? = null

    constructor() {}

    constructor(username: String?, firstName: String?, lastName: String?, email: String?, country: String?, city: String?) {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.country = country
        this.city = city
    }

    constructor(username: String?, firstName: String?, lastName: String?, email: String?, country: String?, city: String?,
                address: String?, dateOfBirth: Date?, phoneNumber: String?) {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.country = country
        this.city = city
        this.address = address
        this.dateOfBirth = dateOfBirth
        this.phoneNumber = phoneNumber
    }
}
