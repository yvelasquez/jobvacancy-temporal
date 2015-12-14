package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicOfferResource {

    private final Logger log = LoggerFactory.getLogger(OfferResource.class);

    @Inject
    private OfferRepository offerRepository;

    @RequestMapping(value = "/publicoffers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Offer> getAllOffers() {
        log.debug("REST request to get all Offers");
        return offerRepository.findAll();
    }
}
