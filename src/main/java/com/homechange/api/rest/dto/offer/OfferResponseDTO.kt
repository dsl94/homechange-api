package com.homechange.api.rest.dto.offer

import com.homechange.api.model.Offer
import com.homechange.api.model.OfferStatus
import com.homechange.api.rest.dto.home.HomeResponseDTO

/**
 * Created by Nemanja on 5/18/17.

 * Response DTO of Offer object
 */
class OfferResponseDTO {

    var id: Long? = null
    var startDate: String? = null
    var endDate: String? = null
    var details: String? = null
    var country: String? = null
    var city: String? = null
    var status: OfferStatus? = null
    var home: HomeResponseDTO? = null

    constructor() {

    }

    constructor(offer: Offer?, formatedStartDate: String, formatedEndDate: String) {
        this.id = offer?.id
        this.details = offer?.details
        this.country = offer?.country
        this.city = offer?.city
        this.status = offer?.status
        this.startDate = formatedStartDate
        this.endDate = formatedEndDate
        this.home = HomeResponseDTO(offer?.user?.home)
    }
}
