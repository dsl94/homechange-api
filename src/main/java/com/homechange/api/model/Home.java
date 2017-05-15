package com.homechange.api.model;

import javax.persistence.*;

/**
 * Created by Nemanja on 5/15/17.
 *
 * Class that represents home of User
 */
@Entity
public class Home {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer bedroomNumber;
	@Column(nullable = false)
	private Integer bathroomNumber;
	@Column(nullable = false)
	private PropertyType propertyType = PropertyType.APARTMENT;
	@Column(nullable = false)
	private String description;
	private Boolean tv = true;
	private Boolean internet = true;
	private Boolean airConditioning = true;
	private Boolean heating = true;
	private Boolean petsAllowed = false;
	//TODO ADD PICTURES

	@OneToOne(mappedBy = "home")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
