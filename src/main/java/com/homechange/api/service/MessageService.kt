package com.homechange.api.service

import com.homechange.api.error.MessageException
import com.homechange.api.rest.dto.message.*

/**
 * Created by Nemanja on 5/21/17.

 * Service interface for Message entity
 */
interface MessageService {

    /**
     * Method for sending message
     * @param messageDTO Message DTO
     * *
     * @return Message response dto
     */
    @Throws(MessageException::class)
    fun sendMessage(messageDTO: SendMessageDTO): MessageResponseDTO

    /**
     * Method used for replying in messages
     * @param replyMessageDTO Reply on message DTO
     * *
     * @return Messages response
     * *
     * @throws MessageException
     */
    @Throws(MessageException::class)
    fun replyOnMessage(replyMessageDTO: ReplyMessageDTO): MessagesResponseDTO

    /**
     * Method that reads all users messages
     * *
     * @return List of messages
     */
    @Throws(MessageException::class)
    fun getUsersMessages(): UsersMessagesDTO
}
