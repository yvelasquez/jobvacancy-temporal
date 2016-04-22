package com.jobvacancy.repository;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Offer entity.
 */
public interface OfferRepository extends JpaRepository<Offer,Long> {

    List<Offer> findByCompany(Company company);
}
