package com.homechange.api.service;

import com.homechange.api.error.OfferException;
import com.homechange.api.model.Offer;
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO;
import com.homechange.api.rest.dto.offer.OfferResponseDTO;

/**
 * Created by Nemanja on 5/18/17.
 */
public interface OfferService {

	/**
	 * Method for creating offer from request
	 * @param requestDTO Request DTO of Offer
	 * @return Offer Response
	 */
	OfferResponseDTO createOffer(CreateOfferRequestDTO requestDTO) throws OfferException;

	/**
	 * Method for updating existing offer
	 * @param offer New offer
	 * @return New offer response
	 * @throws OfferException
	 */
	OfferResponseDTO updateOffer(Offer offer) throws OfferException;

	/**
	 * Method for deactivating some offer
	 * @param id Offer id
	 * @return Response offer
	 * @throws OfferException
	 */
	OfferResponseDTO deactivateOffer(Long id) throws OfferException;
}
