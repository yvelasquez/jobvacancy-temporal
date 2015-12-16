package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.JobApplication;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.repository.JobApplicationRepository;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.service.MailService;
import com.jobvacancy.web.rest.dto.JobApplicationDTO;
import com.jobvacancy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class JobApplicationResource {

    private final Logger log = LoggerFactory.getLogger(JobApplicationResource.class);

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private JobApplicationRepository jobApplicationRepository;

    @Inject
    private MailService mailService;

    @RequestMapping(value = "/applications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobApplication> createJobApplication(@Valid @RequestBody JobApplicationDTO jobApplication) throws URISyntaxException {
        log.debug("REST request to save JobApplication : {}", jobApplication);
        Offer offer = offerRepository.findOne(jobApplication.getOfferId());
        JobApplication application = new JobApplication();
        application.setOffer(offer);
        application.setApplicantEmail(jobApplication.getEmail());
        application.setApplicatName(jobApplication.getFullname());
        JobApplication result = jobApplicationRepository.save(application);

        this.mailService.sendApplication(jobApplication.getEmail(), offer);

        return ResponseEntity.created(new URI("/api/offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("Job Application", result.getId().toString()))
            .body(result);

    }
}
