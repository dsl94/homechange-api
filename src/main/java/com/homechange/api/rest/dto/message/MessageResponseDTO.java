package com.homechange.api.rest.dto.message;

import com.homechange.api.model.Message;

/**
 * Created by Nemanja on 5/22/17.
 *
 * DTO Used for displaying messages
 */
public class MessageResponseDTO {

	private Long messageId;
	private String sentBy;
	private String sentTo;
	private String dateAndTime;
	private String message;

	public MessageResponseDTO() {
	}

	// Contructor that maps Message to this
	public MessageResponseDTO(Message message, String dateAndTime) {
		this.messageId = message.getId();
		this.sentBy = message.getSender().getUsername();
		this.sentTo = message.getRecipient().getUsername();
		this.message = message.getMessage();
		this.dateAndTime = dateAndTime;
	}

	public String getSentBy() {
		return sentBy;
	}

	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}

	public String getSentTo() {
		return sentTo;
	}

	public void setSentTo(String sentTo) {
		this.sentTo = sentTo;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
}
