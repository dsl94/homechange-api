package com.homechange.api.rest.dto.user;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Nemanja on 5/14/17.
 *
 * Base User DTO class, contains everything except password and id
 * Required fields for creating are marked with not null anotation
 */
public class UserDTO {

	@NotNull
	private String username;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	@NotNull
	private String country;
	@NotNull
	private String city;
	private String address;
	private Date dateOfBirth;
	private String phoneNumber;

	public UserDTO() {
	}

	public UserDTO(String username, String firstName, String lastName, String email, String country, String city) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.city = city;
	}

	public UserDTO(String username, String firstName, String lastName, String email, String country, String city,
				   String address, Date dateOfBirth, String phoneNumber) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
