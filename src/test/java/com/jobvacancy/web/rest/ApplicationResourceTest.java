package com.jobvacancy.web.rest;

import com.jobvacancy.Application;
import com.jobvacancy.domain.JobApplication;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.JobApplicationRepository;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.repository.UserRepository;
import com.jobvacancy.service.MailService;
import com.jobvacancy.web.rest.dto.JobApplicationDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ApplicationResourceTest {

    private static final String APPLICANT_FULLNAME = "THE APPLICANT";
    private static final String APPLICANT_EMAIL = "APPLICANT@TEST.COM";
    private MockMvc restMockMvc;

    private static final long OFFER_ID = 1;
    private static final String OFFER_TITLE = "SAMPLE_TEXT";

    @Mock
    private MailService mailService;

    @Mock
    private OfferRepository offerRepository;

    @Inject
    private JobApplicationRepository jobApplicationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private WebApplicationContext webApplicationContext;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private Offer offer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Optional<User> user = userRepository.findOneByLogin("user");
        offer = new Offer();
        offer.setTitle(OFFER_TITLE);
        offer.setId(OFFER_ID);
        offer.setCompany(user.get().getCompany());
        when(offerRepository.findOne(OFFER_ID)).thenReturn(offer);
        JobApplicationResource jobApplicationResource = new JobApplicationResource();
        ReflectionTestUtils.setField(jobApplicationResource, "offerRepository", offerRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "jobApplicationRepository", jobApplicationRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "mailService", mailService);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(jobApplicationResource)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    @Transactional
    public void createJobApplication() throws Exception {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setEmail(APPLICANT_EMAIL);
        dto.setFullname(APPLICANT_FULLNAME);
        dto.setOfferId(OFFER_ID);

        doNothing().when(mailService).sendApplication(APPLICANT_EMAIL, offer);

        restMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());

        Mockito.verify(mailService).sendApplication(APPLICANT_EMAIL, offer);
    }

    @Test
    @Transactional
    public void getJobApplicationsByOffer() throws Exception {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplicantEmail(APPLICANT_EMAIL);
        jobApplication.setApplicatName(APPLICANT_FULLNAME);
        jobApplication.setOffer(offer);
        this.jobApplicationRepository.save(jobApplication);

        restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        restMockMvc.perform(get("/api/applications/{offerId}", offer.getId()).with(user("user")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].applicantEmail").value(hasItem(APPLICANT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].applicatName").value(hasItem(APPLICANT_FULLNAME.toString())));
    }

    @Test
    @Transactional
    public void getJobApplicationsByOfferNotRetrieveFromOtherOffers() throws Exception {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setApplicantEmail(APPLICANT_EMAIL);
        jobApplication.setApplicatName(APPLICANT_FULLNAME);
        jobApplication.setOffer(offer);
        this.jobApplicationRepository.save(jobApplication);

        restMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        Long unExistingOffer = offer.getId() + 1;
        restMockMvc.perform(get("/api/applications/{offerId}", unExistingOffer).with(user("user")))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*]").isEmpty());
    }
}
