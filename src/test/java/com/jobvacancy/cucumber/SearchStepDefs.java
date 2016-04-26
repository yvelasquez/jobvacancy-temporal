package com.jobvacancy.cucumber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.repository.CompanyRepository;
import com.jobvacancy.repository.OfferRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchStepDefs {

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private CompanyRepository companyRepository;
    
    private List<Offer> offers;
    
    private Company company;

    @Given("^the \"([^\"]*)\" company published offer for \"([^\"]*)\"$")
    public void theCompanyRegisteredOn( String companyName, String title ) throws Throwable {
        this.company = new Company();
        this.company.setName(companyName);
//        this.company.setSubscriptionType(Subscription.Type.STANDARD);
        companyRepository.save( company );
        
    	Offer offer = new Offer();
    	offer.setTitle( title );
    	offer.setDescription( "-" );
    	offer.setLocation( "-" );
    	offer.setCompany(company);

    	offerRepository.save( offer );
    }

    @When("^I search for active offers$")
    public void i_search_for_active_offers() throws Throwable {    	
    	offers = offerRepository.findAll();
    }

    @Then("^I should find offer with title \"([^\"]*)\"$")
    public void i_should_find_offer_with_title( String title ) throws Throwable {
    	assertThat( offers, containsInAnyOrder( hasProperty( "title", is(title) ) ) );
    }
}
