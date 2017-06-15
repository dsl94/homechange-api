package com.homechange.api.error

/**
 * Created by Nemanja on 5/14/17.

 * Class that will be used as return in JSON format when there is some error
 */
class ErrorMessage {

    var errorCode: ErrorCode = ErrorCode.GENERAL_ERROR
    var errorMessage: String = "Something went wrong"

    constructor() {}

    constructor(errorCode: ErrorCode, errorMessage: String) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }
}
