package com.jobvacancy.repository;

import com.jobvacancy.domain.JobApplication;

import java.time.ZonedDateTime;
import java.util.List;

import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nicopaez on 12/16/15.
 */
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {

    //@Query("select jobApplication from JobApplication jobApplication where jobApplication.offer.id = ?#{offerId}")
    //List<JobApplication> findByOfferId(Long offerId);

    List<JobApplication> findByOffer(Offer offer);
}
