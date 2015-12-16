package com.jobvacancy.cucumber;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import cucumber.api.java.en.Given;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobvacancy.Application;
import com.jobvacancy.web.rest.UserResource;

import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@WebAppConfiguration
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
public class JobApplicationStepDefs {

    @Given("^java programmer offer$")
    public void java_programmer_offer() throws Throwable {

    }

    @When("^I apply to this offer$")
    public void i_apply_to_this_offer() throws Throwable {

    }

    @Then("^an email to sent to the owner of the offer$")
    public void an_email_to_sent_to_the_owner_of_the_offer() throws Throwable {

    }

    @Then("^the application is registered in the system$")
    public void the_application_is_registered_in_the_system() throws Throwable {

    }

}
