package com.homechange.api.service;

import com.homechange.api.error.MessageException;
import com.homechange.api.rest.dto.message.*;

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

	/**
	 * Method used for replying in messages
	 * @param replyMessageDTO Reply on message DTO
	 * @return Messages response
	 * @throws MessageException
	 */
	MessagesResponseDTO replyOnMessage(ReplyMessageDTO replyMessageDTO) throws MessageException;

	/**
	 * Method that reads all users messages
	 * @param  username Username
	 * @return List of messages
	 */
	UsersMessagesDTO getUsersMessages(String username) throws MessageException;
}
