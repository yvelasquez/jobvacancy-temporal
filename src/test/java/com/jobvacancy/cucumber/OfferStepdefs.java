package com.jobvacancy.cucumber;

import com.jobvacancy.Application;
import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.User;
import com.jobvacancy.repository.CompanyRepository;
import com.jobvacancy.repository.JobApplicationRepository;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.repository.UserRepository;
import com.jobvacancy.service.UserService;
import com.jobvacancy.web.rest.JobApplicationResource;
import com.jobvacancy.web.rest.TestUtil;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.StrictAssertions;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@Transactional
public class OfferStepdefs {

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private CompanyRepository companyRepository;

    @Inject
    private JobApplicationRepository jobApplicationRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private Offer offer;

    private User user;

    @Inject
    private UserService userService;

    private MockMvc restOfferMockMvc;

    @Inject
    private WebApplicationContext webApplicationContext;
    private ResultActions result;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        JobApplicationResource jobApplicationResource = new JobApplicationResource();
        ReflectionTestUtils.setField(jobApplicationResource, "offerRepository", offerRepository);
        ReflectionTestUtils.setField(jobApplicationResource, "jobApplicationRepository", jobApplicationRepository);

        this.restOfferMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Given("^the user \"([^\"]*)\" with company \"([^\"]*)\"$")
    public void theUserWithCompany(String userName, String companyName) throws Throwable {
        Company company = new Company();
        company.setName(companyName);
        companyRepository.save(company);

        String login = userName.toLowerCase();
        String email = userName + "@example.com";
        user = userService.createUserInformation(login,"Passw0rd!", userName, userName, email, "en");
        user.setCompany(company);
        userRepository.save(user);
    }

    @Given("^the user \"([^\"]*)\" with no company$")
    public void theUserWithNoCompany(String userName) throws Throwable {
        String login = userName.toLowerCase();
        String email = userName + "@example.com";
        user = userService.createUserInformation(login,"Passw0rd!", userName, userName, email, "en");
        userRepository.save(user);
    }

    @When("^he posts an offer with title \"([^\"]*)\"$")
    public void hePostsAnOfferWithTitle(String offerTitle) throws Throwable {

        offer = new Offer();
        offer.setLocation("Baires");
        offer.setTitle(offerTitle);

        // Create the Offer
        this.result = restOfferMockMvc.perform(post("/api/offers")
            .with(csrf())
            .with(SecurityMockMvcRequestPostProcessors.user(user.getLogin()))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .content(TestUtil.convertObjectToJsonBytes(offer)));
    }

    @Then("^the offer is listed in public offers$")
    public void theOfferIsListedInPublicOffers() throws Throwable {
        restOfferMockMvc.perform(get("/api/publicoffers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].title").value(hasItem(offer.getTitle())));
    }

    @Then("^the offer is not listed in public offers$")
    public void theOfferIsNotListedInPublicOffers() throws Throwable {
        restOfferMockMvc.perform(get("/api/publicoffers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].title").value(not(hasItem(offer.getTitle()))));
    }

    @Then("^the offer is created$")
    public void theOfferIsCreated() throws Throwable {
        this.result.andExpect(status().isCreated());
    }

    @Then("^the offer is not created$")
    public void theOfferIsNotCreated() throws Throwable {
        this.result.andExpect(status().isUnprocessableEntity());
    }
}
