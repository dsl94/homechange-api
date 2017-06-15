package com.homechange.api.repository

import com.homechange.api.model.Offer
import com.homechange.api.rest.dto.offer.OfferResponseDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Created by Nemanja on 5/17/17.

 * Repository used for offers
 */
interface OfferRepository : JpaRepository<Offer, Long> {

    /**
     * Method that searches all active offers by country
     * @param country Country
     * *
     * @return List of offers
     */
    @Query("SELECT o FROM Offer o WHERE o.status=0 AND upper(o.country) like %?1%")
    fun findByCountryIgnoreCaseAndStatusActive(country: String): List<Offer>?

    /**
     * Method that finds all active offers by city
     * @param city
     * *
     * @return
     */
    @Query("SELECT o FROM Offer o WHERE o.status=0 AND upper(o.city) like %?1%")
    fun findByCityIgnoreCaseAndStatusActive(city: String): List<Offer>?
}
