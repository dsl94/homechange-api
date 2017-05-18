package com.homechange.api.rest.dto.offer;

import com.homechange.api.model.Offer;
import com.homechange.api.model.OfferStatus;
import com.homechange.api.rest.dto.home.HomeResponseDTO;
import com.homechange.api.rest.dto.user.UserResponseDTO;

/**
 * Created by Nemanja on 5/18/17.
 *
 * Response DTO of Offer object
 */
public class OfferResponseDTO {

	private Long id;
	private String startDate;
	private String endDate;
	private String details;
	private String country;
	private String city;
	private OfferStatus status;
	private HomeResponseDTO home;

	public OfferResponseDTO(){

	}

	public OfferResponseDTO(Offer offer, String formatedStartDate, String formatedEndDate) {
		this.id = offer.getId();
		this.details = offer.getDetails();
		this.country = offer.getCountry();
		this.city = offer.getCity();
		this.status = offer.getStatus();
		this.startDate = formatedStartDate;
		this.endDate = formatedEndDate;
		this.home = new HomeResponseDTO(offer.getUser().getHome());
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

	public HomeResponseDTO getHome() {
		return home;
	}

	public void setHome(HomeResponseDTO home) {
		this.home = home;
	}
}
