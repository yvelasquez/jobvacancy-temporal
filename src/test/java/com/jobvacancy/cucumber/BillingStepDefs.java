package com.jobvacancy.cucumber;

import com.jobvacancy.domain.Company;
import com.jobvacancy.service.BillingService;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by nicopaez on 4/23/16.
 */
public class BillingStepDefs {

    private Company company;
    private int actualAmountToPay;

    @Given("^the \"([^\"]*)\" company with a basic subscription$")
    public void theCompanyWithABasicSubscription(String companyName) throws Throwable {
        this.company = new Company();
        this.company.setName(companyName);
    }

    @When("^calculate billing$")
    public void calculateBilling() throws Throwable {
        BillingService billingService = new BillingService();
        actualAmountToPay = billingService.calculateFor(company);
    }

    @Then("^the total amount to pay is: (\\d+)$")
    public void theTotalAmountToPayIs(int expectedAmountToPay) throws Throwable {
        assertThat(actualAmountToPay,is(expectedAmountToPay));
    }
}
