package com.homechange.api.error

/**
 * Created by Nemanja on 5/14/17.

 * Error codes enumeration used with exceptions
 */
enum class ErrorCode {

    // Code when user is not found
    USER_NOT_FOUND,
    // Code when there are some invalid parameters in request
    INVALID_PARAMETERS,
    // If no specific mapping exist for error then GENERAL_ERROR code is used
    GENERAL_ERROR,
    // Code used when you try to register player with used username
    USERNAME_ALREADY_IN_USE,
    // Code used when you try to register player with used email
    EMAIL_ALREADY_IN_USE,
    // Code when user already have home
    USER_ALREADY_HAVE_HOME,
    // Code when you try to delete or edit home which is in offer
    HOME_IS_IN_OFFER,
    // Code when you try to delete Home that does not exist
    HOME_NOT_FOUND,
    // Code when user trys to deactivate offer that is not his own
    NOT_USERS_OFFER,
    // Code when offer is not found
    OFFER_NOT_FOUND,
    // Code when offer is not active
    OFFER_IS_NOT_ACTIVE,
    // Code when user tries to send message to it's own offer
    OFFER_IS_USERS_OFFER,
    // Code when message does not exist
    MESSAGE_NOT_FOUND,
    // Code when offer is not the same as in old message
    OFFER_NOT_THE_SAME,
    // Code when users are not the same as in last message
    USERS_NOT_THE_SAME,
    // Code when thread is not users thread
    THREAD_IS_NOT_USERS,
    // Code when offers date is larger then current date
    OFFER_NOT_ENDED,
    // Code when you try to review user without enoug communication
    NOT_ENOUGH_COMMUNICATION,
    // Code when number of stars is not beetwen 0 and 10
    INVALID_NUMBER_OF_STARS,
    // Code when offer has review
    OFFER_ALREADY_HAVE_REVIEW,
    // Code when review is not found
    REVIEW_NOT_FOUND,
    // Code when you try to respond on review that's not for logged in user
    REVIEW_IS_NOT_FOR_LOGGED_IN_USER,
    // Code when review already have response
    REVIEW_ALREADY_HAVE_RESPONSE
}
