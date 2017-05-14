package com.homechange.api.error;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Class that will be used as return in JSON format when there is some error
 */
public class ErrorMessage {

	private ErrorCode errorCode;
	private String errorMessage;

	public ErrorMessage() {
	}

	public ErrorMessage(ErrorCode errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
