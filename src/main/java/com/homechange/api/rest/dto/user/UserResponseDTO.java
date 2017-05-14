package com.homechange.api.rest.dto.user;

import com.homechange.api.model.User;

import java.util.Date;

/**
 * Created by Nemanja on 5/14/17.
 *
 * DTO Used in responses, contains user id
 */
public class UserResponseDTO extends UserDTO {

	private Long userId;

	public UserResponseDTO() {
		super();
	}

	/**
	 * Constructor to be used for mapping repository object to response
	 * @param user User
	 */
	public UserResponseDTO(User user) {
		super(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getCountry(),
				user.getCity(), user.getAddress(), user.getDateOfBirth(), user.getPhoneNumber());
		this.userId = user.getId();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
