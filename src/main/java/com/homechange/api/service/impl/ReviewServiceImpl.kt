package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.ReviewException
import com.homechange.api.model.OfferStatus
import com.homechange.api.repository.OfferRepository
import com.homechange.api.repository.ReviewRepository
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.review.GuestReviewRequestDTO
import com.homechange.api.rest.dto.review.OwnerReviewRequestDTO
import com.homechange.api.rest.dto.review.ReviewResponseDTO
import com.homechange.api.service.MessageService
import com.homechange.api.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by Nemanja on 6/15/17.
 *
 * Implementation of review service
 */
@Service
class ReviewServiceImpl : ReviewService{

    @Autowired
    val reviewRepository: ReviewRepository? = null

    @Autowired
    val offerRepository: OfferRepository? = null

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val messageService: MessageService? = null

    /**
     * Method used when owner of the apartment does a review
     * This method also closes offer
     * @return Review response dto
     */
    @Throws(ReviewException::class)
    override fun ownerReview(reviewDTO: OwnerReviewRequestDTO): ReviewResponseDTO {
        // Getting logged in username
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.principal as String
        val reviewer = userRepository?.findByUsernameIgnoreCase(loggedInUsername) ?: throw ReviewException("User not found", ErrorCode.USER_NOT_FOUND)
        // Starting validations
        // First validate if offer exist
        val offer = offerRepository?.findOne(reviewDTO.offerId) ?: throw ReviewException("Offer with that ID does not exist", ErrorCode.OFFER_NOT_FOUND)
        // Then check if logged in user is owner of the offer
        if (!offer.user?.username.equals(loggedInUsername, true)) {
            throw ReviewException("Logged in user is not the owner of the offer", ErrorCode.OFFER_IS_USERS_OFFER)
        }
        // Check if offer is still active, after review it will be closed
        if (offer.status == OfferStatus.INACTIVE)  {
            throw ReviewException("Offer is already closed, you can't review it", ErrorCode.OFFER_IS_NOT_ACTIVE)
        }
        // Check if offer already has review by this user
        // number of reviews in this case must be zero
        // TODO - This validation can be removed
        val reviews = offer.reviews
        if (reviews != null){
            if (reviews.isNotEmpty()) {
                throw ReviewException("This offer already have review by owner", ErrorCode.OFFER_ALREADY_HAVE_REVIEW)
            }
        }
        // Then validate if current date is greater then offers end date
        val currentDateAndTime = Date()
        if (currentDateAndTime.before(offer.endDate)) {
            throw ReviewException("You can't do a review for an offer that didn't ended yet", ErrorCode.OFFER_NOT_ENDED)
        }
        // Validate if user for review exist
        val userForReview = userRepository.findByUsernameIgnoreCase(reviewDTO.userToReviewUsername) ?: throw ReviewException("" +
                "User that you are trying to review does not exist", ErrorCode.USER_NOT_FOUND)
        // Then check if user's had some communication
        val messages = messageService?.messagesInThread(offer, loggedInUsername, userForReview.username)
        if (messages?.size!! < 2) {
            throw ReviewException("You can't review user if you did not have at least two messages", ErrorCode.NOT_ENOUGH_COMMUNICATION)
        }
        // Check number of stars
        if (reviewDTO.numberOfStars!! <0 || reviewDTO.numberOfStars!! > 10) {
            throw ReviewException("Number of stars must be between 0 and 10", ErrorCode.INVALID_NUMBER_OF_STARS)
        }
        // Validation completed, now we can create review
        val reviewForSave = reviewDTO.mapToReview()
        reviewForSave.offer = offer
        reviewForSave.reviewer = reviewer
        reviewForSave.reviewedUser = userForReview

        // Saving review
        val result = reviewRepository?.save(reviewForSave)
        // Changing offer to inactive and saving it
        offer.status = OfferStatus.INACTIVE
        offerRepository.save(offer)

        // Mapping to response and returning it
        return ReviewResponseDTO(result)
    }

    /**
     * Method used when guest is reviewing owner
     *
     * @return Review response DTO
     */
    override fun guestReview(reviewDTO: GuestReviewRequestDTO): ReviewResponseDTO {
        // First we try to load review on which we are responding
        val oldReview = reviewRepository?.findOne(reviewDTO.ownerReviewId) ?: throw ReviewException("Review on which you are trying to respond is not found", ErrorCode.REVIEW_NOT_FOUND)
        // Getting logged in user
        val auth = SecurityContextHolder.getContext().authentication
        val loggedInUsername = auth.principal as String
        val loggedInUser = userRepository?.findByUsernameIgnoreCase(loggedInUsername) ?: throw ReviewException("User not found", ErrorCode.USER_NOT_FOUND)
        // Then we check if user form review on which we are responding is same as logged user
        if (!loggedInUsername.equals(oldReview.reviewedUser?.username, true)) {
            throw ReviewException("You can't respond to a review that's not meant for logged in user", ErrorCode.REVIEW_IS_NOT_FOR_LOGGED_IN_USER)
        }
        // Then we check if there is already answer to a review
        val reviews = oldReview.offer?.reviews
        if (reviews != null) {
            if (reviews.size != 1) {
                throw ReviewException("Review already have response", ErrorCode.REVIEW_ALREADY_HAVE_RESPONSE)
            }
        }
        // We also have to validate number of stars
        if (reviewDTO.numberOfStars!! < 0 || reviewDTO.numberOfStars!! > 10) {
            throw ReviewException("Number of stars must be between 0 and 10", ErrorCode.INVALID_NUMBER_OF_STARS)
        }
        // If all is ok, we create review
        val reviewForSave = reviewDTO.mapToReview()
        reviewForSave.reviewedUser = oldReview.reviewer
        reviewForSave.reviewer = loggedInUser
        reviewForSave.offer = oldReview.offer

        // Then we save it
        val result = reviewRepository.save(reviewForSave)

        // Then map it and return it
        return ReviewResponseDTO(result)
    }
}