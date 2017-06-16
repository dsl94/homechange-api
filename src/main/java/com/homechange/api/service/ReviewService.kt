package com.homechange.api.service

import com.homechange.api.error.ReviewException
import com.homechange.api.rest.dto.review.OwnerReviewRequestDTO
import com.homechange.api.rest.dto.review.ReviewResponseDTO

/**
 * Created by Nemanja on 6/15/17.
 *
 * Interface for review service
 */
interface ReviewService {

    /**
     * Method used when owner of the apartment does a review
     *
     * @return Review response dto
     */
    @Throws(ReviewException::class)
    fun ownerReview(reviewDTO: OwnerReviewRequestDTO) : ReviewResponseDTO
}