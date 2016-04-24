package com.jobvacancy.domain;

/**
 * Created by nicopaez on 4/24/16.
 */
public class BasicSubscription extends Subscription{
    @Override
    public int calculateAmountToBill() {
        return 0;
    }
}
