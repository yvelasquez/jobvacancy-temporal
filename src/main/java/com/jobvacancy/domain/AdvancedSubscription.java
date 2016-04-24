package com.jobvacancy.domain;

/**
 * Created by nicopaez on 4/24/16.
 */
public class AdvancedSubscription extends Subscription {
    public AdvancedSubscription(Company company) {
        super();
    }

    @Override
    public int calculateAmountToBill() {
        return 200;
    }
}
