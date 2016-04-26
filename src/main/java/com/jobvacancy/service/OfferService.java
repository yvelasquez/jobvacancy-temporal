package com.jobvacancy.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.jobvacancy.domain.Offer;
import com.jobvacancy.domain.Subscription;
import com.jobvacancy.repository.OfferRepository;

@Component
public class OfferService {

    @Inject
    private OfferRepository offerRepository;
    
	public List<Offer> rankAllOffers(){
		List<Offer> offers = offerRepository.findByCompanySubscriptionType( Subscription.Type.ADVANCED );
		offers.addAll( offerRepository.findByCompanySubscriptionType( Subscription.Type.BASIC ) );
		offers.addAll( offerRepository.findByCompanySubscriptionType( Subscription.Type.ONDEMAND ) );
		offers.addAll( offerRepository.findByCompanySubscriptionType( Subscription.Type.STANDARD ) );

		return offers;
	}
}
