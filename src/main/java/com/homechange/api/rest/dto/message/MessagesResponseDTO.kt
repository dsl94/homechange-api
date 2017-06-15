package com.homechange.api.rest.dto.message

import com.homechange.api.rest.dto.offer.OfferResponseDTO

/**
 * Created by Nemanja on 5/22/17.

 * Response used for multiple messages in one offer
 * It's not data class since it has method in it
 */
class MessagesResponseDTO {

    var numberOfMessages: Int? = null
    var offerId: Long? = null
    var messages: List<MessageResponseDTO>? = null

    constructor() {}

    constructor(numberOfMessages: Int?, offerId: Long?, messages: List<MessageResponseDTO>) {
        this.numberOfMessages = numberOfMessages
        this.offerId = offerId
        this.messages = messages
    }
}
