package com.jobvacancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.Subscription;

/**
 * Spring Data JPA repository for the Offer entity.
 */
public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findByCompany(Company company);

	List<Offer> findAll();
	
	List<Offer> findByCompanySubscriptionType( Subscription.Type subscription );
}
