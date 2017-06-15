package com.homechange.api.rest.dto.message

import com.homechange.api.model.Message

/**
 * Created by Nemanja on 5/22/17.
 *
 * DTO Used for displaying messages
 * It's not data class since it has method in it
 */
class MessageResponseDTO {

    var messageId: Long? = null
    var sentBy: String? = null
    var sentTo: String? = null
    var dateAndTime: String? = null
    var message: String? = null

    constructor() {}

    // Constructor that maps Message to this
    constructor(message: Message?, dateAndTime: String?) {
        this.messageId = message?.id
        this.sentBy = message?.sender?.username
        this.sentTo = message?.recipient?.username
        this.message = message?.message
        this.dateAndTime = dateAndTime
    }
}
