package com.jobvacancy.cucumber;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.StandardSubscription;
import com.jobvacancy.domain.Subscription;
import com.jobvacancy.service.BillingService;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.time.Instant;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by nicopaez on 4/24/16.
 */
public class BillingStepsDef {

    private Company company;
    private int actualAmountToBill;

    @And("^with a standard subscription$")
    public void withAStandardSubscription() throws Throwable {
        this.company.setSubscriptionType(Subscription.Type.STANDARD);
    }


    @Given("^the \"([^\"]*)\" company registered on \"([^\"]*)\"$")
    public void theCompanyRegisteredOn(String companyName, String registrationDate) throws Throwable {
        LocalDate registationDate = LocalDate.parse(registrationDate);
        this.company = new Company();
        this.company.setName(companyName);
        company.setRegistrationDate(registationDate);
    }

    @When("^calculate billing on \"([^\"]*)\"$")
    public void calculateBillingOn(String billingDate) throws Throwable {
        BillingService billingService =  new BillingService();
        String billingDateTime = billingDate + "T11:00:00.00Z";
        Clock fixedClock = Clock.fixed(java.time.Instant.parse(billingDateTime), ZoneId.systemDefault());
        billingService.setClock(fixedClock);
        actualAmountToBill = billingService.calculateFor(company);
    }

    @Then("^the total amount to bill is: (\\d+)$")
    public void theTotalAmountToBillIs(int expectedAmountToBill) throws Throwable {
        assertThat(actualAmountToBill,is(expectedAmountToBill));
    }

    @And("^the date of the next bill is \"([^\"]*)\"$")
    public void theDateOfTheNextBillIs(String arg0) throws Throwable {

    }
}
