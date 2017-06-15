package com.homechange.api.repository

import com.homechange.api.model.Message
import com.homechange.api.model.Offer
import com.homechange.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Created by Nemanja on 5/21/17.

 * Repository for Message entity
 */
interface MessageRepository : JpaRepository<Message, Long> {

    /**
     * Method that searches all messages for one offer and orders them
     * so the newest will be last in list
     * @param offer Offer
     * *
     * @return List of Messages
     */
    fun findByOfferOrderBySentDateAndTimeAsc(offer: Offer): List<Message>?

    /**
     * Method that finds all messages that user received
     * @param recipient User
     * *
     * @return List of messages
     */
    fun findByRecipientOrderBySentDateAndTimeAsc(recipient: User): List<Message>?

    /**
     * Method that finds all messages that user sent
     * @param sender User
     * *
     * @return List of messages
     */
    fun findBySenderOrderBySentDateAndTimeAsc(sender: User): List<Message>?

    /**
     * Method that finds all messages by sender or by recipient
     * Idea is to use it to find all messages of some user no matter if
     * he sent them or received them, to do that we send both parameters the same
     * @param sender User
     * *
     * @param recipient User
     * *
     * @return List of messages
     */
    fun findBySenderOrRecipientOrderBySentDateAndTimeAsc(sender: User, recipient: User): List<Message>?

    @Query("SELECT m FROM Message m WHERE m.sender=?1 OR m.recipient=?1 GROUP BY m.offer ORDER BY m.sentDateAndTime DESC")
    fun findAndGroup(user: User): List<Message>?
}
