package com.homechange.api.error

/**
 * Created by Nemanja on 5/21/17.

 * Exception for message model
 */
class MessageException
/**
 * Constructs a new exception with the specified detail message.  The
 * cause is not initialized, and may subsequently be initialized by
 * a call to [.initCause].

 * @param message the detail message. The detail message is saved for
 * *                later retrieval by the [.getMessage] method.
 */
(message: String, val errorCode: ErrorCode) : Exception(message)
