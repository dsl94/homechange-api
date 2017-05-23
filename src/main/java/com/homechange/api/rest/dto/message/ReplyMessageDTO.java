package com.homechange.api.rest.dto.message;

import com.homechange.api.model.Message;

import javax.validation.constraints.NotNull;

/**
 * Created by Nemanja on 5/22/17.
 *
 * DTO Used for replying to message
 */
public class ReplyMessageDTO extends SendMessageDTO {

	@NotNull
	private Long messageId;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * Method for mapping DTO to entity
	 *
	 * @return Message
	 */
	@Override
	public Message mapToMessage() {
		return super.mapToMessage();
	}
}
