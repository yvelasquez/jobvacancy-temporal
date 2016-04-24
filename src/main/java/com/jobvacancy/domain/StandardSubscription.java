package com.jobvacancy.domain;

/**
 * Created by nicopaez on 4/24/16.
 */
public class StandardSubscription extends Subscription {
    public StandardSubscription(Company company) {
        super();
    }

    @Override
    public int calculateAmountToBill() {
        return 100;
    }
}
