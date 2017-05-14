package com.homechange.api.rest.dto.user;

import com.homechange.api.model.User;

import javax.validation.constraints.NotNull;

/**
 * Created by Nemanja on 5/14/17.
 *
 * DTO that extends UserDTO
 * It is used for requests for creating user and contains password
 * TODO Maybe change name
 */
public class UserRequestDTO extends UserDTO{

	@NotNull
	private String password;

	public UserRequestDTO(){
		super();
	}

	public UserRequestDTO(String username, String firstName, String lastName, String email, String country, String city,
						  String password) {
		super(username, firstName, lastName, email, country, city);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Method that maps DTO to User class to be used in repository
	 * @return User class
	 */
	public User mapToUser(){
		User user = new User();
		user.setUsername(this.getUsername());
		user.setFirstName(this.getFirstName());
		user.setLastName(this.getLastName());
		user.setEmail(this.getEmail());
		user.setCountry(this.getCountry());
		user.setCity(this.getCity());
		user.setAddress(this.getAddress());
		user.setDateOfBirth(this.getDateOfBirth());
		user.setPhoneNumber(this.getPhoneNumber());
		user.setPassword(this.password);

		return user;
	}
}
