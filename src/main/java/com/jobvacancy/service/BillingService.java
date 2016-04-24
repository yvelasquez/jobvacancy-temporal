package com.jobvacancy.service;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Subscription;

/**
 * Created by nicopaez on 4/23/16.
 */
public class BillingService {
    public int calculateFor(Company company) {
        Subscription subscription = company.getSubscription();
        return subscription.calculateAmountToBill();
    }
}
