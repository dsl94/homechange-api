package com.homechange.api.rest.dto.message

import com.homechange.api.model.Message

import javax.validation.constraints.NotNull

/**
 * Created by Nemanja on 5/22/17.

 * DTO Used for replying to message
 * It's not data class since it has method in it
 */
class ReplyMessageDTO : SendMessageDTO() {

    @NotNull
    var messageId: Long? = null

    /**
     * Method for mapping DTO to entity

     * @return Message
     */
    override fun mapToMessage(): Message {
        return super.mapToMessage()
    }
}
