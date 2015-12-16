package com.jobvacancy.cucumber;


import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.nio.cs.Surrogate.is;

import javax.inject.Inject;

import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.JobApplicationRepository;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.repository.UserRepository;
import com.jobvacancy.service.MailService;
import com.jobvacancy.web.rest.JobApplicationResource;
import com.jobvacancy.web.rest.TestUtil;
import com.jobvacancy.web.rest.dto.JobApplicationDTO;
import cucumber.api.java.en.Given;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.web.rest.UserResource;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.Optional;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class JobApplicationStepDefs {

    private static final String APPLICANT_FULLNAME = "THE APPLICANT";
    private static final String APPLICANT_EMAIL = "APPLICANT@TEST.COM";
    private MockMvc restMockMvc;

    private static final long OFFER_ID = 1;
    private static final String OFFER_TITLE = "SAMPLE_TEXT";

    @Mock
    private MailService mailService;

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private JobApplicationRepository jobApplicationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private Offer offer;

    private long applicationCount;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        JobApplicationResource jobApplicationResource = new JobApplicationResource();
        ReflectionTestUtils.setField(jobApplicationResource, "offerRepository", offerRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "jobApplicationRepository", jobApplicationRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "mailService", mailService);

        this.restMockMvc = MockMvcBuilders.standaloneSetup(jobApplicationResource)
            .setMessageConverters(jacksonMessageConverter).build();

    }

    @Given("^java programmer offer$")
    public void java_programmer_offer() throws Throwable {
        Optional<User> user = userRepository.findOneByLogin("user");
        offer = new Offer();
        offer.setTitle("Java Programmer");
        offer.setId(OFFER_ID);
        offer.setUser(user.get());
        applicationCount = jobApplicationRepository.count();
    }

    @When("^I apply to this offer$")
    public void i_apply_to_this_offer() throws Throwable {
        JobApplicationDTO dto = new JobApplicationDTO();
        dto.setEmail(APPLICANT_EMAIL);
        dto.setFullname(APPLICANT_FULLNAME);
        dto.setOfferId(OFFER_ID);

        doNothing().when(mailService).sendApplication(APPLICANT_EMAIL, offer);

        restMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dto)))
            .andExpect(status().isCreated());
    }

    @Then("^an email to sent to the owner of the offer$")
    public void an_email_to_sent_to_the_owner_of_the_offer() throws Throwable {
        Mockito.verify(mailService).sendApplication(APPLICANT_EMAIL, offer);
    }

    @Then("^the application is registered in the system$")
    public void the_application_is_registered_in_the_system() throws Throwable {
        long actualApplicationCount = jobApplicationRepository.count();
        assertTrue(actualApplicationCount == applicationCount +1);
    }

}
