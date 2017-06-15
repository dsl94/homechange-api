package com.homechange.api.rest.dto.message

/**
 * Created by Nemanja on 5/23/17.

 * DTO Used for displaying users messages, just info about them not whole thread
 */
class UsersMessageDTO {

    var offerCity: String? = null
    var offerId: Long? = null
    var offerStartDate: String? = null
    var offerEndDate: String? = null
    var usernameOfRecipient: String? = null
    var lastMessageId: Long? = null

    constructor() {}

    constructor(offerCity: String, offerId: Long, offerStartDate: String, offerEndDate: String, usernameOfRecipient: String, lastMessageId: Long?) {
        this.offerCity = offerCity
        this.offerId = offerId
        this.offerStartDate = offerStartDate
        this.offerEndDate = offerEndDate
        this.usernameOfRecipient = usernameOfRecipient
        this.lastMessageId = lastMessageId
    }
}
