package com.homechange.api.service;

import com.homechange.api.error.MessageException;
import com.homechange.api.rest.dto.message.MessageResponseDTO;
import com.homechange.api.rest.dto.message.SendMessageDTO;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Service interface for Message entity
 */
public interface MessageService {

	/**
	 * Method for sending message
	 * @param messageDTO Message DTO
	 * @return Message response dto
	 */
	MessageResponseDTO sendMessage(SendMessageDTO messageDTO) throws MessageException;
}
