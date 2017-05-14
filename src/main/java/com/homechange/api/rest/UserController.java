package com.homechange.api.rest;

import com.homechange.api.error.ErrorCode;
import com.homechange.api.error.ErrorMessage;
import com.homechange.api.error.UserException;
import com.homechange.api.rest.dto.user.UserRequestDTO;
import com.homechange.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Rest controller for User entity
 */
@RestController()
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		try {
			return ResponseEntity.ok(userService.save(userRequestDTO));
		} catch (UserException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}

	@RequestMapping(value = "/findbyid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findUserById(@RequestParam("id") Long id) {
		try {
			return ResponseEntity.ok(userService.get(id));
		} catch (UserException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleMyException(Exception  exception) {
		return ResponseEntity.badRequest().body(new ErrorMessage(ErrorCode.INVALID_PARAMETERS,
				"Some of mandatory parameters are not present"));
	}
}
