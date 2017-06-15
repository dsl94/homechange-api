package com.homechange.api.rest.dto.offer

/**
 * Created by Nemanja on 5/21/17.

 * DTO Used in offer search, it returns number of results
 * and list of OfferResponseDTOs
 */
class OffersDTO {

    var numberOfResults: Int? = null
    var offers: List<OfferResponseDTO>? = null
}
