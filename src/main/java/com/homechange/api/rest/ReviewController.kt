package com.homechange.api.rest

import com.homechange.api.error.ErrorMessage
import com.homechange.api.error.ReviewException
import com.homechange.api.rest.dto.review.GuestReviewRequestDTO
import com.homechange.api.rest.dto.review.OwnerReviewRequestDTO
import com.homechange.api.rest.dto.review.ReviewResponseDTO
import com.homechange.api.service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Created by Nemanja on 6/16/17.
 */
@RestController
@RequestMapping("/api/sec")
class ReviewController {

    @Autowired
    val reviewService: ReviewService? = null

    @CrossOrigin
    @RequestMapping(value = "/ownerreview", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun ownerReview(@Valid @RequestBody requestDTO: OwnerReviewRequestDTO): ResponseEntity<Any> {
        try{
            return ResponseEntity.ok(reviewService?.ownerReview(requestDTO))
        } catch (e: ReviewException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/guestreview", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun guestReview(@Valid @RequestBody requestDTO: GuestReviewRequestDTO): ResponseEntity<Any> {
        try{
            return ResponseEntity.ok(reviewService?.guestReview(requestDTO))
        } catch (e: ReviewException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }
    }
}