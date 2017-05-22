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
import com.homechange.api.rest.dto.message.MessageResponseDTO;
import com.homechange.api.rest.dto.message.SendMessageDTO;
import com.homechange.api.service.MessageService;
import com.homechange.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
}
