package com.homechange.api.error;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Error codes enumeration used with exceptions
 */
public enum ErrorCode {

	// Code when user is not found
	USER_NOT_FOUND,
	// Code when there are some invalid parameters in request
	INVALID_PARAMETERS,
	// If no specific mapping exist for error then GENERAL_ERROR code is used
	GENERAL_ERROR,
	// Code used when you try to register player with used username
	USERNAME_ALREADY_IN_USE,
	// Code used when you try to register player with used email
	EMAIL_ALREADY_IN_USE
}
