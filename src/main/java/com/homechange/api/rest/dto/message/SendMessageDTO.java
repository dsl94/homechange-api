package com.homechange.api.rest.dto.message;

import com.homechange.api.model.Message;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Nemanja on 5/22/17.
 *
 * DTO Used for message sending
 */
public class SendMessageDTO {

	@NotNull
	private Long offerId;
	@NotNull
	private String message;

	public SendMessageDTO() {
	}

	public SendMessageDTO(Long offerId, String message) {
		this.offerId = offerId;
		this.message = message;
	}

	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Method for mapping DTO to entity
	 * @return Message
	 */
	public Message mapToMessage(){
		Message result = new Message();
		result.setMessage(this.getMessage());
		// Setting current moment for sent time
		result.setSentDateAndTime(new Date());

		return result;
	}
}
