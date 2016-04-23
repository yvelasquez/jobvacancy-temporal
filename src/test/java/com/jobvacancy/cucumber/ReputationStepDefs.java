package com.jobvacancy.cucumber;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ReputationStepDefs {

    private Company company;

    @Given("^the \"([^\"]*)\" company$")
    public void theCompany(String companyName) throws Throwable {
        this.company = new Company();
        this.company.setName(companyName);
    }

    @When("^no offers published$")
    public void noOffersPublished() throws Throwable {
    }

    @Then("^reputation is \"([^\"]*)\"$")
    public void reputationIs(Integer expectedReputation) throws Throwable {
        company.updateReputation();
        assertEquals(expectedReputation, company.getReputation());
    }

    @When("^\"([^\"]*)\" offers published in the last month$")
    public void offersPublishedInTheLastMonth(Integer offersCount) throws Throwable {
        Set<Offer> offers = new HashSet<>();
        for (long i=0; i < offersCount; i++) {
            Offer offer = new Offer();
            offer.setId(i);
            offer.setTitle("offer" + i);
            offers.add(offer);
        }
        company.setOfferss(offers);
    }
}
