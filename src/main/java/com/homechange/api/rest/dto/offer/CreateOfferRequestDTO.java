package com.homechange.api.rest.dto.offer;

import com.homechange.api.model.Offer;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Nemanja on 5/18/17.
 *
 * Offer request DTO object, used for creating new offer
 */
public class CreateOfferRequestDTO {

	@NotNull
	private Date startDate;
	@NotNull
	private Date endDate;
	@NotNull
	private String details;

	public CreateOfferRequestDTO() {
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * Method used to create Offer from this DTO
	 * @return Offer
	 */
	public Offer mapToOffer(){
		Offer result = new Offer();
		result.setStartDate(this.startDate);
		result.setEndDate(this.endDate);
		result.setDetails(this.details);

		return result;
	}
}
