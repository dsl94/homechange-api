package com.homechange.api.rest.dto.message

import com.homechange.api.model.Message

import javax.validation.constraints.NotNull
import java.util.Date

/**
 * Created by Nemanja on 5/22/17.

 * DTO Used for message sending
 */
open class SendMessageDTO {

    @NotNull
    var offerId: Long? = null
    @NotNull
    var message: String? = null

    constructor() {}

    constructor(offerId: Long?, message: String) {
        this.offerId = offerId
        this.message = message
    }

    /**
     * Method for mapping DTO to entity
     * @return Message
     */
    open fun mapToMessage(): Message {
        val result = Message()
        result.message = this.message
        // Setting current moment for sent time
        result.sentDateAndTime = Date()

        return result
    }
}
