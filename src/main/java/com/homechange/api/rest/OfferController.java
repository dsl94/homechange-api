package com.homechange.api.rest;

import com.homechange.api.error.ErrorMessage;
import com.homechange.api.error.OfferException;
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO;
import com.homechange.api.service.OfferService;
import com.homechange.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Nemanja on 5/18/17.
 *
 * Controller for Offer
 */
@RestController
@RequestMapping("/api")
public class OfferController {

	@Autowired
	private OfferService offerService;

	@RequestMapping(value = "/sec/createoffer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity createOffer(@Valid @RequestBody CreateOfferRequestDTO requestDTO){
		try {
			return ResponseEntity.ok(offerService.createOffer(requestDTO));
		} catch (OfferException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}

	@RequestMapping(value = "/sec/deactivateffer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deactivateOffer(@RequestParam Long id){
		try {
			return ResponseEntity.ok(offerService.deactivateOffer(id));
		} catch (OfferException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}

	@RequestMapping(value = "/offerdetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity offerDetails(@RequestParam Long id){
		try {
			return ResponseEntity.ok(offerService.getOfferById(id));
		} catch (OfferException e) {
			return ResponseEntity.badRequest().body(new ErrorMessage(e.getErrorCode(), e.getMessage()));
		}
	}
}
