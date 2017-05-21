package com.homechange.api.error;

/**
 * Created by Nemanja on 5/21/17.
 *
 * Exception for message model
 */
public class MessageException extends Exception {

	private ErrorCode errorCode;

	/**
	 * Constructs a new exception with the specified detail message.  The
	 * cause is not initialized, and may subsequently be initialized by
	 * a call to {@link #initCause}.
	 *
	 * @param message the detail message. The detail message is saved for
	 *                later retrieval by the {@link #getMessage()} method.
	 */
	public MessageException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
