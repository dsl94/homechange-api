package com.homechange.api.rest.dto.home;

import com.homechange.api.model.Home;
import com.homechange.api.model.PropertyType;

import javax.validation.constraints.NotNull;

/**
 * Created by Nemanja on 5/15/17.
 */
public class CreateHomeRequestDTO {

	@NotNull
	private String username;
	@NotNull
	private Integer bedroomNumber;
	@NotNull
	private Integer bathroomNumber;
	@NotNull
	private PropertyType propertyType;
	@NotNull
	private String description;
	private Boolean tv = true;
	private Boolean internet = true;
	private Boolean airConditioning = true;
	private Boolean heating = true;
	private Boolean petsAllowed = false;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getBedroomNumber() {
		return bedroomNumber;
	}

	public void setBedroomNumber(Integer bedroomNumber) {
		this.bedroomNumber = bedroomNumber;
	}

	public Integer getBathroomNumber() {
		return bathroomNumber;
	}

	public void setBathroomNumber(Integer bathroomNumber) {
		this.bathroomNumber = bathroomNumber;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getTv() {
		return tv;
	}

	public void setTv(Boolean tv) {
		this.tv = tv;
	}

	public Boolean getInternet() {
		return internet;
	}

	public void setInternet(Boolean internet) {
		this.internet = internet;
	}

	public Boolean getAirConditioning() {
		return airConditioning;
	}

	public void setAirConditioning(Boolean airConditioning) {
		this.airConditioning = airConditioning;
	}

	public Boolean getHeating() {
		return heating;
	}

	public void setHeating(Boolean heating) {
		this.heating = heating;
	}

	public Boolean getPetsAllowed() {
		return petsAllowed;
	}

	public void setPetsAllowed(Boolean petsAllowed) {
		this.petsAllowed = petsAllowed;
	}

	/**
	 * Method for maping dto to home
	 * @return Home instance
	 */
	public Home mapToHome() {
		Home home = new Home();
		home.setBedroomNumber(this.bedroomNumber);
		home.setBathroomNumber(this.bathroomNumber);
		home.setDescription(this.description);
		home.setPropertyType(this.propertyType);
		home.setAirConditioning(this.airConditioning);
		home.setHeating(this.heating);
		home.setInternet(this.internet);
		home.setPetsAllowed(this.petsAllowed);
		home.setTv(this.tv);

		return home;
	}
}
