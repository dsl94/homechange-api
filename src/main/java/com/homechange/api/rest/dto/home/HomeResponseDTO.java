package com.homechange.api.rest.dto.home;

import com.homechange.api.model.Home;
import com.homechange.api.model.PropertyType;
import com.homechange.api.rest.dto.user.UserResponseDTO;

/**
 * Created by Nemanja on 5/15/17.
 *
 * DTO used for response purpose
 */
public class HomeResponseDTO {

	private Integer bedroomNumber;
	private Integer bathroomNumber;
	private PropertyType propertyType;
	private String description;
	private Boolean tv;
	private Boolean internet;
	private Boolean airConditioning;
	private Boolean heating;
	private Boolean petsAllowed;
	private UserResponseDTO user;

	public HomeResponseDTO() {
	}

	public HomeResponseDTO(Home home) {
		this.bedroomNumber = home.getBedroomNumber();
		this.bathroomNumber = home.getBathroomNumber();
		this.propertyType = home.getPropertyType();
		this.description = home.getDescription();
		this.tv = home.getTv();
		this.internet = home.getInternet();
		this.airConditioning = home.getAirConditioning();
		this.heating = home.getHeating();
		this.petsAllowed = home.getPetsAllowed();
		this.user = new UserResponseDTO(home.getUser());
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

	public UserResponseDTO getUser() {
		return user;
	}

	public void setUser(UserResponseDTO user) {
		this.user = user;
	}
}
