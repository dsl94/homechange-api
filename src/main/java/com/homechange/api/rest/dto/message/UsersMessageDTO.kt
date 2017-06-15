package com.homechange.api.rest.dto.message

/**
 * Created by Nemanja on 5/23/17.

 * DTO Used for diplsaying users messages, just info about them now whole thread
 */
class UsersMessageDTO {

    var offerCity: String? = null
    var offerStartDate: String? = null
    var offerEndDate: String? = null
    var usernameOfRecipient: String? = null
    var lastMessageId: Long? = null

    constructor() {}

    constructor(offerCity: String, offerStartDate: String, offerEndDate: String, usernameOfRecipient: String, lastMessageId: Long?) {
        this.offerCity = offerCity
        this.offerStartDate = offerStartDate
        this.offerEndDate = offerEndDate
        this.usernameOfRecipient = usernameOfRecipient
        this.lastMessageId = lastMessageId
    }
}
