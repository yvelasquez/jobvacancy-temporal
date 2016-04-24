package com.jobvacancy.cucumber;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.Subscription;
import com.jobvacancy.service.BillingService;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by nicopaez on 4/23/16.
 */
public class SubscriptionBillingStepDefs {

    private final BillingService billingService;
    private Company company;
    private int actualAmountToPay;

    public SubscriptionBillingStepDefs() {
        billingService = new BillingService();
    }

    @Given("^the \"([^\"]*)\" company with a basic subscription$")
    public void theCompanyWithABasicSubscription(String companyName) throws Throwable {
        createCompany(companyName);
    }

    private void createCompany(String companyName) {
        this.company = new Company();
        this.company.setName(companyName);
        this.company.setRegistrationDate(LocalDate.of(2014, 1, 1));
    }

    @When("^calculate billing$")
    public void calculateBilling() throws Throwable {
        actualAmountToPay = billingService.calculateFor(company);
    }

    @Then("^the total amount to pay is: (\\d+)$")
    public void theTotalAmountToPayIs(int expectedAmountToPay) throws Throwable {
        assertThat(actualAmountToPay,is(expectedAmountToPay));
    }

    @Given("^the \"([^\"]*)\" company with a standard subscription$")
    public void theCompanyWithAStandardSubscription(String companyName) throws Throwable {
        createCompany(companyName);
        this.company.setSubscriptionType(Subscription.Type.STANDARD);
    }

    @Given("^the \"([^\"]*)\" company with a on-demand subscription$")
    public void theCompanyWithAOnDemandSubscription(String companyName) throws Throwable {
        this.createCompany(companyName);
        this.company.setSubscriptionType(Subscription.Type.ONDEMAND);    }

    @Given("^the \"([^\"]*)\" company with a advanced subscription$")
    public void theCompanyWithAAdvancedSubscription(String companyName) throws Throwable {
        this.createCompany(companyName);
        this.company.setSubscriptionType(Subscription.Type.ADVANCED);
    }

    @When("^\"([^\"]*)\" published offers$")
    public void publishedOffers(Integer offerCount) throws Throwable {
        Set<Offer> offers = new HashSet<>();
        for (long i=0; i < offerCount; i++) {
            Offer offer = new Offer();
            offer.setId(i);
            offer.setTitle("offer" + i);
            offers.add(offer);
        }
        company.setOfferss(offers);
    }
}
