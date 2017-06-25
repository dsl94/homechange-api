package com.homechange.api.rest

import com.homechange.api.error.ErrorMessage
import com.homechange.api.error.OfferException
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO
import com.homechange.api.service.OfferService
import com.homechange.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

/**
 * Created by Nemanja on 5/18/17.

 * Controller for Offer
 */
@RestController
@RequestMapping("/api")
class OfferController {

    @Autowired
    private val offerService: OfferService? = null

    @CrossOrigin
    @RequestMapping(value = "/sec/createoffer", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun createOffer(@Valid @RequestBody requestDTO: CreateOfferRequestDTO): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(offerService?.createOffer(requestDTO))
        } catch (e: OfferException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/sec/deactivateffer", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun deactivateOffer(@RequestParam id: Long?): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(offerService?.deactivateOffer(id))
        } catch (e: OfferException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/offerdetails", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun offerDetails(@RequestParam id: Long?): ResponseEntity<Any> {
        try {
            return ResponseEntity.ok(offerService?.getOfferById(id))
        } catch (e: OfferException) {
            return ResponseEntity.badRequest().body(ErrorMessage(e.errorCode, e.message?:""))
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/findoffer", method = arrayOf(RequestMethod.GET), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun findOffers(@RequestParam query: String): ResponseEntity<Any> {
        return ResponseEntity.ok(offerService?.findOffers(query))
    }
}
