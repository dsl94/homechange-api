package com.homechange.api.rest.dto.home

import com.homechange.api.model.Home
import com.homechange.api.model.PropertyType

import javax.validation.constraints.NotNull

/**
 * Created by Nemanja on 5/15/17.
 *
 * It's not data class since it has method in it
 */
class CreateHomeRequestDTO {

    @NotNull
    var bedroomNumber: Int? = null
    @NotNull
    var bathroomNumber: Int? = null
    @NotNull
    var propertyType: PropertyType? = null
    @NotNull
    var description: String? = null
    var tv: Boolean = true
    var internet: Boolean = true
    var airConditioning: Boolean = true
    var heating: Boolean = true
    var petsAllowed: Boolean = false

    /**
     * Method for maping dto to home
     * @return Home instance
     */
    fun mapToHome(): Home {
        val home = Home()
        home.bedroomNumber = this.bedroomNumber
        home.bathroomNumber = this.bathroomNumber
        home.description = this.description
        home.propertyType = this.propertyType
        home.airConditioning = this.airConditioning
        home.heating = this.heating
        home.internet = this.internet
        home.petsAllowed = this.petsAllowed
        home.tv = this.tv

        return home
    }
}
