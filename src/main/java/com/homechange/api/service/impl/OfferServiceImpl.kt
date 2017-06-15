package com.homechange.api.service.impl

import com.homechange.api.error.ErrorCode
import com.homechange.api.error.OfferException
import com.homechange.api.model.Offer
import com.homechange.api.model.OfferStatus
import com.homechange.api.model.User
import com.homechange.api.repository.OfferRepository
import com.homechange.api.repository.UserRepository
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO
import com.homechange.api.rest.dto.offer.OfferResponseDTO
import com.homechange.api.rest.dto.offer.OffersDTO
import com.homechange.api.service.OfferService
import com.homechange.api.util.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

import java.util.ArrayList
import java.util.stream.Collectors

/**
 * Created by Nemanja on 5/18/17.

 * Implementation of Offer Service
 */
@Service
class OfferServiceImpl : OfferService {

    @Autowired
    private val offerRepository: OfferRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null

    /**
     * Method for creating offer from request

     * @param requestDTO Request DTO of Offer
     * *
     * @return Offer Response
     */
    @Throws(OfferException::class)
    override fun createOffer(requestDTO: CreateOfferRequestDTO): OfferResponseDTO {
        val forSave = requestDTO.mapToOffer()
        // Get logged in username
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.principal as String
        // Get user from repository
        val user = userRepository?.findByUsernameIgnoreCase(username) ?: throw OfferException("User not found", ErrorCode.USER_NOT_FOUND)
        // Check if user exists, it should always exist
        if (user.home == null) {
            throw OfferException("User must have home before posting an offer", ErrorCode.HOME_NOT_FOUND)
        }
        forSave.user = user
        forSave.country = user.country
        forSave.city = user.city
        forSave.status = OfferStatus.ACTIVE
        // Save offer
        val result = offerRepository?.save(forSave)

        // Map result to response and return
        return OfferResponseDTO(result, Utils.formatDate(result?.startDate), Utils.formatDate(result?.endDate))
    }

    /**
     * Method for updating existing offer
     * @param offer New offer
     * *
     * @return New offer response
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    override fun updateOffer(offer: Offer): OfferResponseDTO {
        TODO()
    }

    /**
     * Method for deactivating some offer
     * @param id Offer id
     * *
     * @return Response offer
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    override fun deactivateOffer(id: Long?): OfferResponseDTO {
        // Try to find offer by id and throw exception if it doesn't exist
        val offer = offerRepository?.findOne(id) ?: throw OfferException("Offer with that id is not found", ErrorCode.OFFER_NOT_FOUND)
// Get logged in user, it's username
        val auth = SecurityContextHolder.getContext().authentication
        val username = auth.principal as String
        // Check if offer belongs to logged in user
        if (!username.equals(offer.user?.username!!, ignoreCase = true)) {
            throw OfferException("Offer does not belong to logged in user", ErrorCode.NOT_USERS_OFFER)
        }
        // Check if offer is deactivated already
        if (offer.status == OfferStatus.INACTIVE) {
            throw OfferException("Offer is not acttive so we can't deactivate it", ErrorCode.OFFER_IS_NOT_ACTIVE)
        }

        // If all is ok, update status and save
        offer.status = OfferStatus.INACTIVE
        val result = offerRepository.save(offer)

        // Map to response and return
        return OfferResponseDTO(offer, Utils.formatDate(offer.startDate), Utils.formatDate(offer.endDate))
    }

    /**
     * Method that load offer by its id
     * @param id Id of offer
     * *
     * @return Offer response
     * *
     * @throws OfferException
     */
    @Throws(OfferException::class)
    override fun getOfferById(id: Long?): OfferResponseDTO {
        // Try to find offer
        val offer = offerRepository?.findOne(id) ?: throw OfferException("Offer with that id is not found", ErrorCode.OFFER_NOT_FOUND)
// If exist, map and return
        return OfferResponseDTO(offer, Utils.formatDate(offer.startDate), Utils.formatDate(offer.endDate))
    }

    /**
     * Method that searches for all offers by country or by city
     * @param query Country or City name
     * *
     * @return OffersDTO
     */
    override fun findOffers(query: String): OffersDTO {
        // Get offers by country
        val resultByCountry = getOffersByCountry(query)
        // Get offers by city
        val resultByCity = getOffersByCity(query)
        val result = OffersDTO()
        result.numberOfResults = resultByCountry.numberOfResults!! + resultByCity.numberOfResults!!
        val resultList = ArrayList<OfferResponseDTO>()
        // Join results
        resultList.addAll(resultByCity.offers!!)
        resultList.addAll(resultByCountry.offers!!)
        result.offers = resultList

        return result
    }

    /**
     * Private helper method that searches by Country
     * @param country
     * *
     * @return
     */
    private fun getOffersByCountry(country: String): OffersDTO {
        val offers = offerRepository?.findByCountryIgnoreCaseAndStatusActive(country.toUpperCase())
        val result = OffersDTO()
        val offerResponseDTOS = ArrayList<OfferResponseDTO>()
        for (offer in offers!!) {
            val offerResponseDTO = OfferResponseDTO(offer, Utils.formatDate(offer.startDate),
                    Utils.formatDate(offer.endDate))
            offerResponseDTOS.add(offerResponseDTO)
        }
        result.numberOfResults = offerResponseDTOS.size
        result.offers = offerResponseDTOS

        return result
    }

    /**
     * Private helper method that searches by City
     * @param city
     * *
     * @return
     */
    private fun getOffersByCity(city: String): OffersDTO {
        val offers = offerRepository?.findByCityIgnoreCaseAndStatusActive(city.toUpperCase())
        val result = OffersDTO()
        val offerResponseDTOS = ArrayList<OfferResponseDTO>()
        for (offer in offers!!) {
            val offerResponseDTO = OfferResponseDTO(offer, Utils.formatDate(offer.startDate),
                    Utils.formatDate(offer.endDate))
            offerResponseDTOS.add(offerResponseDTO)
        }
        result.numberOfResults = offerResponseDTOS.size
        result.offers = offerResponseDTOS

        return result
    }

}
