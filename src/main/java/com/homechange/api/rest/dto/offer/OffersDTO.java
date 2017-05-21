package com.homechange.api.rest.dto.offer;

import java.util.List;

/**
 * Created by Nemanja on 5/21/17.
 *
 * DTO Used in offer search, it returns number of results
 * and list of OfferResponseDTOs
 */
public class OffersDTO {

	private Integer numberOfResults;
	private List<OfferResponseDTO> offers;

	public Integer getNumberOfResults() {
		return numberOfResults;
	}

	public void setNumberOfResults(Integer numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	public List<OfferResponseDTO> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferResponseDTO> offers) {
		this.offers = offers;
	}
}
