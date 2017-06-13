package com.homechange.api.service.impl;

import com.homechange.api.error.ErrorCode;
import com.homechange.api.error.MessageException;
import com.homechange.api.model.Message;
import com.homechange.api.model.Offer;
import com.homechange.api.model.OfferStatus;
import com.homechange.api.model.User;
import com.homechange.api.repository.MessageRepository;
import com.homechange.api.repository.OfferRepository;
import com.homechange.api.repository.UserRepository;
import com.homechange.api.rest.dto.message.*;
import com.homechange.api.service.MessageService;
import com.homechange.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Implementation of message service
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method for sending message
	 *
	 * @param messageDTO Message DTO
	 * @return Message response dto
	 */
	@Override
	public MessageResponseDTO sendMessage(SendMessageDTO messageDTO) throws MessageException {
		// First, before all check if offer exist
		Offer offer = offerRepository.findOne(messageDTO.getOfferId());
		if (offer == null) {
			throw new MessageException("Offer with that ID is not found", ErrorCode.OFFER_NOT_FOUND);
		}
		// Then check if offer is active
		if (!offer.getStatus().equals(OfferStatus.ACTIVE)) {
			throw new MessageException("That offer is not active", ErrorCode.OFFER_IS_NOT_ACTIVE);
		}
		// If offer exist and is active check if users that send message is not the owner of the offer
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usernameOfSender = (String) auth.getPrincipal();
		String usernameOfReceiver = offer.getUser().getUsername();
		if (usernameOfSender.equalsIgnoreCase(usernameOfReceiver)) {
			throw new MessageException("User can not send message to itself", ErrorCode.OFFER_IS_USERS_OFFER);
		}

		// All validation is passed, we can send message now
		Message forSave = messageDTO.mapToMessage();
		User sender = userRepository.findByUsernameIgnoreCase(usernameOfSender);
		User receiver = userRepository.findByUsernameIgnoreCase(usernameOfReceiver);
		forSave.setSender(sender);
		forSave.setRecipient(receiver);
		forSave.setOffer(offer);
		// save message to db
		Message result = messageRepository.save(forSave);

		return new MessageResponseDTO(result, Utils.formatDateAndTime(result.getSentDateAndTime()));
	}

	/**
	 * Method used for replying in messages
	 * @param replyMessageDTO Reply on message DTO
	 * @return Messages response
	 * @throws MessageException
	 */
	@Override
	public MessagesResponseDTO replyOnMessage(ReplyMessageDTO replyMessageDTO) throws MessageException {
		// First step is to do all the validations
		// Check if old message exist
		Message oldMessage = messageRepository.findOne(replyMessageDTO.getMessageId());
		if (oldMessage == null) {
			throw new MessageException("Message that you are trying to reply to does not exist", ErrorCode.MESSAGE_NOT_FOUND);
		}
		// Check if offers are in same thread, to do that we check if offers have the same offer
		// And first we check if offer exist
		Offer oldOffer = oldMessage.getOffer();
		Offer newOffer = offerRepository.findOne(replyMessageDTO.getOfferId());
		if (newOffer == null) {
			throw new MessageException("Offer with that id does not exist", ErrorCode.OFFER_NOT_FOUND);
		}
		if (!oldOffer.getId().equals(newOffer.getId())) {
			throw new MessageException("Messages are not in the same thread, offers are not the same for new and old message", ErrorCode.OFFER_NOT_THE_SAME);
		}
		// Then get logged in username, he is sender
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usernameOfSender = (String) auth.getPrincipal();
		// Get username of user that receives the message
		String usernameOfReceiver = oldMessage.getRecipient().getUsername();
		if (usernameOfSender.equalsIgnoreCase(usernameOfReceiver)) {
			usernameOfReceiver = oldMessage.getSender().getUsername();
		}

		// Now we check if users are the same as in old message
		if ((usernameOfReceiver.equalsIgnoreCase(oldMessage.getRecipient().getUsername()) ||
			 usernameOfReceiver.equalsIgnoreCase(oldMessage.getSender().getUsername())) &&
				(usernameOfSender.equalsIgnoreCase(oldMessage.getRecipient().getUsername()) ||
				 usernameOfSender.equalsIgnoreCase(oldMessage.getSender().getUsername()))) {
			// Here everything is ok, we can save message
			Message forSave = replyMessageDTO.mapToMessage();
			User sender = userRepository.findByUsernameIgnoreCase(usernameOfSender);
			User receiver = userRepository.findByUsernameIgnoreCase(usernameOfReceiver);
			// Additional validation just in case
			if (sender == null || receiver == null) {
				throw new MessageException("User with that id does not exist", ErrorCode.USER_NOT_FOUND);
			}
			forSave.setSender(sender);
			forSave.setRecipient(receiver);
			forSave.setOffer(newOffer);
			messageRepository.save(forSave);
			List<Message> threadMessages = messagesInThread(newOffer, usernameOfSender, usernameOfReceiver);
			return mapToMessagesResponse(threadMessages, newOffer.getId());
		} else {
			throw new MessageException("Users are not the same", ErrorCode.USERS_NOT_THE_SAME);
		}
	}

	/**
	 * Method that reads all users messages
	 * @param username Username
	 * @return List of messages
	 */
	@Override
	public UsersMessagesDTO getUsersMessages(String username) throws MessageException {
		/*User user = userRepository.findByUsernameIgnoreCase(username);
		// Validate user
		if (user == null) {
			throw new MessageException("User with that username not found", ErrorCode.USER_NOT_FOUND);
		}
		List<Message> allMessages = messageRepository.findBySenderOrRecipientOrderBySentDateAndTimeAsc(user, user);
		// Now convert this to response
		UsersMessagesDTO response = new UsersMessagesDTO();
		List<UsersMessageDTO> messageDTOS = new ArrayList<>();
		for (Message message : allMessages) {
			String otherUser;
			if (!message.getRecipient().getUsername().equalsIgnoreCase(username)) {
				otherUser = message.getRecipient().getUsername();
			} else {
				otherUser = message.getSender().getUsername();
			}
			UsersMessageDTO messageDTO = new UsersMessageDTO(message.getOffer().getCity(),
					Utils.INSTANCE.formatDate(message.getOffer().getStartDate()), )
		}*/
		return null;
	}

	/**
	 * Helper method that finds all messages in thread
	 * @param offer Offer
	 * @return Messages in thread
	 */
	private List<Message> messagesInThread(Offer offer, String username1, String username2){
		List<Message> messagesForOffer = messageRepository.findByOfferOrderBySentDateAndTimeAsc(offer);
		List<Message> threadMessages = new ArrayList<>();

		for (Message message : messagesForOffer) {
			if (message.getSender().getUsername().equalsIgnoreCase(username1) ||
					message.getRecipient().getUsername().equalsIgnoreCase(username1)) {
				if (message.getSender().getUsername().equalsIgnoreCase(username2) ||
						message.getRecipient().getUsername().equalsIgnoreCase(username2)) {

					threadMessages.add(message);
				}
			}
		}

		return threadMessages;
	}

	/**
	 * Helper method that maps list of messags to response
	 * @param messages List of messages
	 * @return Response dto
	 */
	private MessagesResponseDTO mapToMessagesResponse(List<Message> messages, Long offerId) {
		MessagesResponseDTO result = new MessagesResponseDTO();
		result.setNumberOfMessages(messages.size());
		result.setOfferId(offerId);
		List<MessageResponseDTO> messageResponseDTOs = new ArrayList<>();
		for (Message message : messages) {
			MessageResponseDTO messageResponseDTO = new MessageResponseDTO(message, Utils.formatDateAndTime(message.getSentDateAndTime()));
			messageResponseDTOs.add(messageResponseDTO);
		}
		result.setMessages(messageResponseDTOs);
		return result;
	}
}
