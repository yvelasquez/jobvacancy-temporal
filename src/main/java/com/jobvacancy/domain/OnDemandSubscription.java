package com.jobvacancy.domain;

/**
 * Created by nicopaez on 4/24/16.
 */
public class OnDemandSubscription extends Subscription {

    private final Company company;

    public OnDemandSubscription(Company company) {
        super();
        this.company = company;
    }

    @Override
    public int calculateAmountToBill() {
        return company.getOfferss().size() * 30;
    }
}
