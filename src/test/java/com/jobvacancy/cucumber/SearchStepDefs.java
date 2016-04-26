package com.jobvacancy.cucumber;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.Subscription;
import com.jobvacancy.repository.CompanyRepository;
import com.jobvacancy.repository.OfferRepository;
import com.jobvacancy.service.OfferService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchStepDefs {

    @Inject
    private OfferRepository offerRepository;

    @Inject
    private OfferService offerService;
    
    @Inject
    private CompanyRepository companyRepository;
    
    private List<Offer> offers;
    
    private Company company;
    
    @Given("^the \"([^\"]*)\" company published offer for \"([^\"]*)\"$")
    public void theCompanyRegisteredOn( String companyName, String title ) throws Throwable {
        createOfferForCompany(companyName, title, Subscription.Type.STANDARD);
    }

	private void createOfferForCompany(String companyName, String title, Subscription.Type subscription ) {
		this.company = new Company();
        this.company.setName(companyName);
        this.company.setSubscriptionType(subscription);
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
    	offers = offerService.rankAllOffers();
    }

    @Then("^I should find offer with title \"([^\"]*)\"$")
    public void i_should_find_offer_with_title( String title ) throws Throwable {
    	assertThat( offers, hasItem( hasProperty( "title", is(title) ) ) );
    }
    
    @Given("^the \"([^\"]*)\" company with Advanced subscription published offer for \"([^\"]*)\"$")
    public void the_company_with_Advanced_subscription_published_offer_for(String company, String title) throws Throwable {
    	createOfferForCompany(company, title, Subscription.Type.ADVANCED);
    }

    @Then("^I should first find offer with title \"([^\"]*)\"$")
    public void i_should_first_find_offer_with_title(String title) throws Throwable {
    	assertTrue( offers.size() > 0 );
    	assertThat( offers.get(0), hasProperty( "title", is(title) ) );
    }
}
