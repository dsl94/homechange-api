package com.homechange.api.service.impl;

import com.homechange.api.error.ErrorCode;
import com.homechange.api.error.OfferException;
import com.homechange.api.model.Offer;
import com.homechange.api.model.OfferStatus;
import com.homechange.api.model.User;
import com.homechange.api.repository.OfferRepository;
import com.homechange.api.repository.UserRepository;
import com.homechange.api.rest.dto.offer.CreateOfferRequestDTO;
import com.homechange.api.rest.dto.offer.OfferResponseDTO;
import com.homechange.api.service.OfferService;
import com.homechange.api.service.UserService;
import com.homechange.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nemanja on 5/18/17.
 *
 * Implementation of Offer Service
 */
@Service
public class OfferServiceImpl implements OfferService{

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Method for creating offer from request
	 *
	 * @param requestDTO Request DTO of Offer
	 * @return Offer Response
	 */
	@Override
	public OfferResponseDTO createOffer(CreateOfferRequestDTO requestDTO) throws OfferException {
		Offer forSave = requestDTO.mapToOffer();
		// Get logged in username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		// Get user from repository
		User user = userRepository.findByUsername(username);
		// Check if user exists, it should always exist
		if (user == null) {
			throw new OfferException("User not found", ErrorCode.USER_NOT_FOUND);
		}
		forSave.setUser(user);
		forSave.setCountry(user.getCountry());
		forSave.setCity(user.getCity());
		forSave.setStatus(OfferStatus.ACTIVE);
		// Save offer
		Offer result = offerRepository.save(forSave);

		// Map result to response and return
		return new OfferResponseDTO(result, Utils.formatDate(result.getStartDate()), Utils.formatDate(result.getEndDate()));
	}

}
