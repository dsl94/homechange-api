package com.homechange.api.rest.dto.home

import com.homechange.api.model.Home
import com.homechange.api.model.PropertyType
import com.homechange.api.rest.dto.user.UserResponseDTO

/**
 * Created by Nemanja on 5/15/17.

 * DTO used for response purpose
 *
 * It's not data class since it has method in it
 */
class HomeResponseDTO {

    var bedroomNumber: Int? = null
    var bathroomNumber: Int? = null
    var propertyType: PropertyType? = null
    var description: String? = null
    var tv: Boolean? = null
    var internet: Boolean? = null
    var airConditioning: Boolean? = null
    var heating: Boolean? = null
    var petsAllowed: Boolean? = null
    var user: UserResponseDTO? = null

    constructor() {}

    constructor(home: Home?) {
        this.bedroomNumber = home?.bedroomNumber
        this.bathroomNumber = home?.bathroomNumber
        this.propertyType = home?.propertyType
        this.description = home?.description
        this.tv = home?.tv
        this.internet = home?.internet
        this.airConditioning = home?.airConditioning
        this.heating = home?.heating
        this.petsAllowed = home?.petsAllowed
        this.user = UserResponseDTO(home?.user)
    }
}
