package com.homechange.api.repository;

import com.homechange.api.model.Message;
import com.homechange.api.model.Offer;
import com.homechange.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Repository for Message entity
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

	/**
	 * Method that searches all messages for one offer and orders them
	 * so the newest will be last in list
	 * @param offer Offer
	 * @return List of Messages
	 */
	List<Message> findByOfferOrderBySentDateAndTimeAsc(Offer offer);

	/**
	 * Method that finds all messages that user received
	 * @param recipient User
	 * @return List of messages
	 */
	List<Message> findByRecipientOrderBySentDateAndTimeAsc(User recipient);

	/**
	 * Method that finds all messages that user sent
	 * @param sender User
	 * @return List of messages
	 */
	List<Message> findBySenderOrderBySentDateAndTimeAsc(User sender);

	/**
	 * Method that finds all messages by sender or by recipient
	 * Idea is to use it to find all messages of some user no matter if
	 * he sent them or received them, to do that we send bot parameters the same
	 * @param sender User
	 * @param recipient User
	 * @return List of messages
	 */
	List<Message> findBySenderOrRecipientOrderBySentDateAndTimeAsc(User sender, User recipient);
}
