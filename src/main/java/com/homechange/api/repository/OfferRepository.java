package com.homechange.api.repository;

import com.homechange.api.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Nemanja on 5/17/17.
 *
 * Repository used for offers
 */
public interface OfferRepository extends JpaRepository<Offer, Long>{

}
