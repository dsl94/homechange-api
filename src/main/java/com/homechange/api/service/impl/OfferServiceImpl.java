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
import com.homechange.api.rest.dto.offer.OffersDTO;
import com.homechange.api.service.OfferService;
import com.homechange.api.service.UserService;
import com.homechange.api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sun.awt.UNIXToolkit;

import javax.rmi.CORBA.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
		User user = userRepository.findByUsernameIgnoreCase(username);
		// Check if user exists, it should always exist
		if (user == null) {
			throw new OfferException("User not found", ErrorCode.USER_NOT_FOUND);
		}
		if (user.getHome() == null) {
			throw new OfferException("User must have home before posting an offer", ErrorCode.HOME_NOT_FOUND);
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

	/**
	 * Method for updating existing offer
	 * @param offer New offer
	 * @return New offer response
	 * @throws OfferException
	 */
	@Override
	public OfferResponseDTO updateOffer(Offer offer) throws OfferException {
		return null;
		// TODO: IMPLEMENT THIS
	}

	/**
	 * Method for deactivating some offer
	 * @param id Offer id
	 * @return Response offer
	 * @throws OfferException
	 */
	@Override
	public OfferResponseDTO deactivateOffer(Long id) throws OfferException {
		// Try to find offer by id and throw exception if it doesn't exist
		Offer offer = offerRepository.findOne(id);
		if (offer == null) {
			throw new OfferException("Offer with that id is not found", ErrorCode.OFFER_NOT_FOUND);
		}
		// Get logged in user, it's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) auth.getPrincipal();
		// Check if offer belongs to logged in user
		if (!username.equalsIgnoreCase(offer.getUser().getUsername())){
			throw new OfferException("Offer does not belong to logged in user", ErrorCode.NOT_USERS_OFFER);
		}
		// Check if offer is deactivated already
		if (offer.getStatus().equals(OfferStatus.INACTIVE)){
			throw new OfferException("Offer is not acttive so we can't deactivate it", ErrorCode.OFFER_IS_NOT_ACTIVE);
		}

		// If all is ok, update status and save
		offer.setStatus(OfferStatus.INACTIVE);
		Offer result = offerRepository.save(offer);

		// Map to response and return
		return new OfferResponseDTO(offer, Utils.formatDate(offer.getStartDate()), Utils.formatDate(offer.getEndDate()));
	}

	/**
	 * Method that load offer by its id
	 * @param id Id of offer
	 * @return Offer response
	 * @throws OfferException
	 */
	@Override
	public OfferResponseDTO getOfferById(Long id) throws OfferException {
		// Try to find offer
		Offer offer = offerRepository.findOne(id);
		if (offer == null) {
			throw new OfferException("Offer with that id is not found", ErrorCode.OFFER_NOT_FOUND);
		}
		// If exist, map and return
		return new OfferResponseDTO(offer, Utils.formatDate(offer.getStartDate()), Utils.formatDate(offer.getEndDate()));
	}

	/**
	 * Method that searches for all offers by country or by city
	 * @param query Country or City name
	 * @return OffersDTO
	 */
	@Override
	public OffersDTO findOffers(String query) {
		// Get offers by country
		OffersDTO resultByCountry = getOffersByCountry(query);
		// Get offers by city
		OffersDTO resultByCity = getOffersByCity(query);
		OffersDTO result = new OffersDTO();
		result.setNumberOfResults(resultByCountry.getNumberOfResults() + resultByCity.getNumberOfResults());
		List<OfferResponseDTO> resultList = new ArrayList<>();
		// Join results
		resultList.addAll(resultByCity.getOffers());
		resultList.addAll(resultByCountry.getOffers());
		result.setOffers(resultList);

		return result;
	}

	/**
	 * Private helper method that searches by Country
	 * @param country
	 * @return
	 */
	private OffersDTO getOffersByCountry(String country) {
		List<Offer> offers = offerRepository.findByCountryIgnoreCaseAndStatusActive(country.toUpperCase());
		OffersDTO result = new OffersDTO();
		List<OfferResponseDTO> offerResponseDTOS = offers.stream().map(offer -> new OfferResponseDTO(offer, Utils.formatDate(offer.getStartDate()),
				Utils.formatDate(offer.getEndDate()))).collect(Collectors.toList());
		result.setNumberOfResults(offerResponseDTOS.size());
		result.setOffers(offerResponseDTOS);

		return result;
	}

	/**
	 * Private helper method that searches by City
	 * @param city
	 * @return
	 */
	private OffersDTO getOffersByCity(String city) {
		List<Offer> offers = offerRepository.findByCityIgnoreCaseAndStatusActive(city.toUpperCase());
		OffersDTO result = new OffersDTO();
		List<OfferResponseDTO> offerResponseDTOS = offers.stream().map(offer -> new OfferResponseDTO(offer, Utils.formatDate(offer.getStartDate()),
				Utils.formatDate(offer.getEndDate()))).collect(Collectors.toList());
		result.setNumberOfResults(offerResponseDTOS.size());
		result.setOffers(offerResponseDTOS);

		return result;
	}

}
