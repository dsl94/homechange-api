package com.homechange.api.rest;

import com.homechange.api.error.ErrorMessage;
import com.homechange.api.error.MessageException;
import com.homechange.api.rest.dto.message.SendMessageDTO;
import com.homechange.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Controller for message entity
 */
@RestController
@RequestMapping("/api/sec")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/sendmessage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity sendMessage(@Valid @RequestBody SendMessageDTO messageDTO){
		try {
			return ResponseEntity.ok(messageService.sendMessage(messageDTO));
		} catch (MessageException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}
}
