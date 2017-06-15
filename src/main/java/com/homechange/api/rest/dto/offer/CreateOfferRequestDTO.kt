package com.homechange.api.rest.dto.offer

import com.homechange.api.model.Offer

import javax.validation.constraints.NotNull
import java.util.Date

/**
 * Created by Nemanja on 5/18/17.

 * Offer request DTO object, used for creating new offer
 */
class CreateOfferRequestDTO {

    @NotNull
    var startDate: Date? = null
    @NotNull
    var endDate: Date? = null
    @NotNull
    var details: String? = null

    /**
     * Method used to create Offer from this DTO
     * @return Offer
     */
    fun mapToOffer(): Offer {
        val result = Offer()
        result.startDate = this.startDate
        result.endDate = this.endDate
        result.details = this.details

        return result
    }
}
