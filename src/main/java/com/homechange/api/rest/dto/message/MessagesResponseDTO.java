package com.homechange.api.rest.dto.message;

import com.homechange.api.rest.dto.offer.OfferResponseDTO;

import java.util.List;

/**
 * Created by Nemanja on 5/22/17.
 *
 * Response used for multiple messages in one offer
 */
public class MessagesResponseDTO {

	private Integer numberOfMessages;
	private Long offerId;
	private List<MessageResponseDTO> messages;

	public MessagesResponseDTO() {
	}

	public MessagesResponseDTO(Integer numberOfMessages, Long offerId, List<MessageResponseDTO> messages) {
		this.numberOfMessages = numberOfMessages;
		this.offerId = offerId;
		this.messages = messages;
	}

	public Integer getNumberOfMessages() {
		return numberOfMessages;
	}

	public void setNumberOfMessages(Integer numberOfMessages) {
		this.numberOfMessages = numberOfMessages;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public List<MessageResponseDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageResponseDTO> messages) {
		this.messages = messages;
	}
}
