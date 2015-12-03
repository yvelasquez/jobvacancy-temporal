package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Offer.
 */
@RestController
@RequestMapping("/api")
public class OfferResource {

    private final Logger log = LoggerFactory.getLogger(OfferResource.class);

    @Inject
    private OfferRepository offerRepository;

    /**
     * POST  /offers -> Create a new offer.
     */
    @RequestMapping(value = "/offers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Offer> createOffer(@Valid @RequestBody Offer offer) throws URISyntaxException {
        log.debug("REST request to save Offer : {}", offer);
        if (offer.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new offer cannot already have an ID").body(null);
        }
        Offer result = offerRepository.save(offer);
        return ResponseEntity.created(new URI("/api/offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("offer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /offers -> Updates an existing offer.
     */
    @RequestMapping(value = "/offers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Offer> updateOffer(@Valid @RequestBody Offer offer) throws URISyntaxException {
        log.debug("REST request to update Offer : {}", offer);
        if (offer.getId() == null) {
            return createOffer(offer);
        }
        Offer result = offerRepository.save(offer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("offer", offer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /offers -> get all the offers.
     */
    @RequestMapping(value = "/offers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Offer> getAllOffers() {
        log.debug("REST request to get all Offers");
        return offerRepository.findAll();
    }

    /**
     * GET  /offers/:id -> get the "id" offer.
     */
    @RequestMapping(value = "/offers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Offer> getOffer(@PathVariable Long id) {
        log.debug("REST request to get Offer : {}", id);
        return Optional.ofNullable(offerRepository.findOne(id))
            .map(offer -> new ResponseEntity<>(
                offer,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /offers/:id -> delete the "id" offer.
     */
    @RequestMapping(value = "/offers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        log.debug("REST request to delete Offer : {}", id);
        offerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("offer", id.toString())).build();
    }
}
