package com.jobvacancy.web.rest;

import com.jobvacancy.Application;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.OfferRepository;

import com.jobvacancy.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OfferResource REST controller.
 *
 * @see OfferResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OfferResourceTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private UserRepository userRepository;

    @Mock
    private UserRepository mockUserRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private WebApplicationContext webApplicationContext;

    private MockMvc restOfferMockMvc;

    private Optional<User> user;

    private Offer offer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OfferResource offerResource = new OfferResource();
        ReflectionTestUtils.setField(offerResource, "offerRepository", offerRepository);

        // TODO: this should be refactored in a based class because is a common concern
        user =  userRepository.findOneByLogin("user");
        when(mockUserRepository.findOneByLogin(Mockito.any())).thenReturn(user);
        ReflectionTestUtils.setField(offerResource, "userRepository", mockUserRepository);

        this.restOfferMockMvc = MockMvcBuilders.standaloneSetup(offerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        offer = new Offer();
        offer.setTitle(DEFAULT_TITLE);
        offer.setLocation(DEFAULT_LOCATION);
        offer.setDescription(DEFAULT_DESCRIPTION);
        offer.setUser(user.get());
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer
        restOfferMockMvc.perform(post("/api/offers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offer)))
                .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offers.get(offers.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOffer.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testOffer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setTitle(null);

        // Create the Offer, which fails.

        restOfferMockMvc.perform(post("/api/offers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offer)))
                .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setLocation(null);

        // Create the Offer, which fails.

        restOfferMockMvc.perform(post("/api/offers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offer)))
                .andExpect(status().isBadRequest());

        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffersReturnsCurrentUserOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Given the way this request is resolved we need to setup a security context
        restOfferMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        // Get all the offers
        restOfferMockMvc.perform(get("/api/offers").with(user("user")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getAllOffersDoesNotReturnOtherUsersOffers() throws Exception {
        // Initialize the database with just an offer that belogs to "user"
        offerRepository.saveAndFlush(offer);

        // Given the way this request is resolved we need to setup a security context
        restOfferMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        // Get all the offers should not return offers for user2
        restOfferMockMvc.perform(get("/api/offers").with(user("user2")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(empty()));
    }

    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

		int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        offer.setTitle(UPDATED_TITLE);
        offer.setLocation(UPDATED_LOCATION);
        offer.setDescription(UPDATED_DESCRIPTION);

        restOfferMockMvc.perform(put("/api/offers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(offer)))
                .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offers.get(offers.size() - 1);
        assertThat(testOffer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOffer.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testOffer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

		int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Get the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Offer> offers = offerRepository.findAll();
        assertThat(offers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
