package com.homechange.api.service

import com.homechange.api.error.OfferException
import com.homechange.api.model.Offer
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO
import com.homechange.api.rest.dto.offer.OfferResponseDTO
import com.homechange.api.rest.dto.offer.OffersDTO

/**
 * Created by Nemanja on 5/18/17.
 */
interface OfferService {

    /**
     * Method for creating offer from request
     * @param requestDTO Request DTO of Offer
     * *
     * @return Offer Response
     */
    @Throws(OfferException::class)
    fun createOffer(requestDTO: CreateOfferRequestDTO): OfferResponseDTO

    /**
     * Method for updating existing offer
     * @param offer New offer
     * *
     * @return New offer response
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    fun updateOffer(offer: Offer): OfferResponseDTO

    /**
     * Method for deactivating some offer
     * @param id Offer id
     * *
     * @return Response offer
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    fun deactivateOffer(id: Long?): OfferResponseDTO

    /**
     * Method that load offer by its id
     * @param id Id of offer
     * *
     * @return Offer response
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    fun getOfferById(id: Long?): OfferResponseDTO

    /**
     * Method that searches for all offers by country or by city
     * @param query Country or City name
     * *
     * @return OffersDTO
     */
    fun findOffers(query: String): OffersDTO
}
