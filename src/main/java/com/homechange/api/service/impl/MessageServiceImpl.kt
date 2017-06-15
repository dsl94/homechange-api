package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.MessageException
import com.homechange.api.model.Message
import com.homechange.api.model.Offer
import com.homechange.api.model.OfferStatus
import com.homechange.api.model.User
import com.homechange.api.repository.MessageRepository
import com.homechange.api.repository.OfferRepository
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.message.*
import com.homechange.api.service.MessageService
import com.homechange.api.util.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import java.util.ArrayList

/**
 * Created by Nemanja on 5/21/17.

 * Implementation of message service
 */
@Service
class MessageServiceImpl : MessageService {

    @Autowired
    private val messageRepository: MessageRepository? = null

    @Autowired
    private val offerRepository: OfferRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    /**
     * Method for sending message

     * @param messageDTO Message DTO
     * *
     * @return Message response dto
     */
    @Throws(MessageException::class)
    override fun sendMessage(messageDTO: SendMessageDTO): MessageResponseDTO {
        // First, before all check if offer exist
        val offer = offerRepository?.findOne(messageDTO.offerId) ?: throw MessageException("Offer with that ID is not found", ErrorCode.OFFER_NOT_FOUND)
// Then check if offer is active
        if (offer.status != OfferStatus.ACTIVE) {
            throw MessageException("That offer is not active", ErrorCode.OFFER_IS_NOT_ACTIVE)
        }
        // If offer exist and is active check if users that send message is not the owner of the offer
        val auth = SecurityContextHolder.getContext().authentication
        val usernameOfSender = auth.principal as String
        val usernameOfReceiver = offer.user?.username
        if (usernameOfSender.equals(usernameOfReceiver?:"", ignoreCase = true)) {
            throw MessageException("User can not send message to itself", ErrorCode.OFFER_IS_USERS_OFFER)
        }

        // All validation is passed, we can send message now
        val forSave = messageDTO.mapToMessage()
        val sender = userRepository?.findByUsernameIgnoreCase(usernameOfSender)
        val receiver = userRepository?.findByUsernameIgnoreCase(usernameOfReceiver)
        forSave.sender = sender
        forSave.recipient = receiver
        forSave.offer = offer
        // save message to db
        val result = messageRepository?.save(forSave)

        return MessageResponseDTO(result, Utils.formatDateAndTime(result?.sentDateAndTime))
    }

    /**
     * Method used for replying in messages
     * @param replyMessageDTO Reply on message DTO
     * *
     * @return Messages response
     * *
     * @throws MessageException
     */
    @Throws(MessageException::class)
    override fun replyOnMessage(replyMessageDTO: ReplyMessageDTO): MessagesResponseDTO {
        // First step is to do all the validations
        // Check if old message exist
        val (_, recipient, sender1, oldOffer) = messageRepository?.findOne(replyMessageDTO.messageId) ?: throw MessageException("Message that you are trying to reply to does not exist", ErrorCode.MESSAGE_NOT_FOUND)
// Check if offers are in same thread, to do that we check if offers have the same offer
        // And first we check if offer exist
        val newOffer = offerRepository?.findOne(replyMessageDTO.offerId) ?: throw MessageException("Offer with that id does not exist", ErrorCode.OFFER_NOT_FOUND)
        if (oldOffer?.id != newOffer.id) {
            throw MessageException("Messages are not in the same thread, offers are not the same for new and old message", ErrorCode.OFFER_NOT_THE_SAME)
        }
        // Then get logged in username, he is sender
        val auth = SecurityContextHolder.getContext().authentication
        val usernameOfSender = auth.principal as String
        // Get username of user that receives the message
        var usernameOfReceiver: String? = recipient?.username
        if (usernameOfSender.equals(usernameOfReceiver, ignoreCase = true)) {
            usernameOfReceiver = sender1?.username
        }

        // Now we check if users are the same as in old message
        if ((usernameOfReceiver.equals(recipient?.username, ignoreCase = true) || usernameOfReceiver.equals(sender1?.username, ignoreCase = true)) && (usernameOfSender.equals(recipient?.username, ignoreCase = true) || usernameOfSender.equals(sender1?.username, ignoreCase = true))) {
            // Here everything is ok, we can save message
            val forSave = replyMessageDTO.mapToMessage()
            val sender = userRepository?.findByUsernameIgnoreCase(usernameOfSender)
            val receiver = userRepository?.findByUsernameIgnoreCase(usernameOfReceiver)
            // Additional validation just in case
            if (sender == null || receiver == null) {
                throw MessageException("User with that id does not exist", ErrorCode.USER_NOT_FOUND)
            }
            forSave.sender = sender
            forSave.recipient = receiver
            forSave.offer = newOffer
            messageRepository.save(forSave)
            val threadMessages = messagesInThread(newOffer, usernameOfSender, usernameOfReceiver)
            return mapToMessagesResponse(threadMessages, newOffer.id)
        } else {
            throw MessageException("Users are not the same", ErrorCode.USERS_NOT_THE_SAME)
        }
    }

    /**
     * Method that reads all users messages
     * @param username Username
     * *
     * @return List of messages
     */
    @Throws(MessageException::class)
    override fun getUsersMessages(): UsersMessagesDTO {
        // Getting logged in username
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.principal as String
        val user = userRepository?.findByUsernameIgnoreCase(username) ?: throw MessageException("User with that username not found", ErrorCode.USER_NOT_FOUND)
        val response = UsersMessagesDTO()
        val allMessages = messageRepository?.findBySenderOrRecipientOrderBySentDateAndTimeAsc(user, user)
        val userMessages = mutableListOf<UsersMessageDTO>()
        val passedOffers = mutableListOf<Long>()
        val passedUsers = mutableListOf<Long>()

        for (message in allMessages!!) {
            val oderUserId = if(message.recipient?.username == username) message.sender?.id else message.recipient?.id
            if (passedOffers.contains(message.offer?.id) && passedUsers.contains(oderUserId)) {
                continue
            } else {
                passedOffers.add(message.offer?.id?:1)
                passedUsers.add(oderUserId?:1)
                val inThread = messagesInThread(message.offer, message.sender?.username, message.recipient?.username)
                val lastMessage = inThread.last()
                val recipient = if(message.recipient?.username == username) message.sender?.username else message.recipient?.username
                val userMessage = UsersMessageDTO(message.offer?.city?:"", message.offer?.id?:1, Utils.formatDate(message.offer?.startDate),
                                                    Utils.formatDate(message.offer?.endDate), recipient?:"", lastMessage.id)
                userMessages.add(userMessage)
            }
        }
        response.numberOfMessages = userMessages.size
        response.messages = userMessages
        return response
    }

    /**
     * Method that returns all thread messages based on id of last message
     * It also considers logged in user
     *
     * @return List of messages
     */
    override fun getThreadMessages(lastMessageId: Long): MessagesResponseDTO {
        val lastMessage = messageRepository?.findOne(lastMessageId) ?: throw MessageException("Message with that ID does not exist", ErrorCode.MESSAGE_NOT_FOUND)
        // Getting logged in username
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUser = auth.principal as String
        if (lastMessage.sender?.username != loggedInUser && lastMessage.recipient?.username != loggedInUser) {
            throw MessageException("Thread that message belongs does not belong to logged in user", ErrorCode.THREAD_IS_NOT_USERS)
        }
        val allMessagesInThread = messagesInThread(lastMessage.offer, lastMessage.sender?.username, lastMessage.recipient?.username)
        val response = mapToMessagesResponse(allMessagesInThread, lastMessage.offer?.id)
        return response
    }

    /**
     * Helper method that finds all messages in thread
     * @param offer Offer
     * *
     * @return Messages in thread
     */
    private fun messagesInThread(offer: Offer?, username1: String?, username2: String?): List<Message> {
        val messagesForOffer = messageRepository?.findByOfferOrderBySentDateAndTimeAsc(offer!!)
        val threadMessages = ArrayList<Message>()

        for (message in messagesForOffer!!) {
            if (message.sender?.username.equals(username1, ignoreCase = true) || message.recipient?.username.equals(username1, ignoreCase = true)) {
                if (message.sender?.username.equals(username2, ignoreCase = true) || message.recipient?.username.equals(username2, ignoreCase = true)) {

                    threadMessages.add(message)
                }
            }
        }

        return threadMessages
    }

    /**
     * Helper method that maps list of messags to response
     * @param messages List of messages
     * *
     * @return Response dto
     */
    private fun mapToMessagesResponse(messages: List<Message>, offerId: Long?): MessagesResponseDTO {
        val result = MessagesResponseDTO()
        result.numberOfMessages = messages.size
        result.offerId = offerId
        val messageResponseDTOs = ArrayList<MessageResponseDTO>()
        for (message in messages) {
            val messageResponseDTO = MessageResponseDTO(message, Utils.formatDateAndTime(message.sentDateAndTime))
            messageResponseDTOs.add(messageResponseDTO)
        }
        result.messages = messageResponseDTOs
        return result
    }
}
