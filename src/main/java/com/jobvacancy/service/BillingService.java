package com.jobvacancy.service;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Subscription;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.time.*;
import java.time.temporal.TemporalUnit;

/**
 * Created by nicopaez on 4/23/16.
 */
public class BillingService {
    private Clock clock = Clock.systemDefaultZone();

    private long daysSinceRegistration;

    public int calculateFor(Company company) {
        Subscription subscription = company.getSubscription();
        int amountToBill = subscription.calculateAmountToBill();
        LocalDate currentDate = LocalDate.now(this.clock);
        Period periodSinceRegistration = Period.between(company.getRegistrationDate(), currentDate);
        if (periodSinceRegistration.getDays() < 15 && periodSinceRegistration.getMonths() == 0) {
            amountToBill = amountToBill /2;
        }
        return amountToBill;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Clock getClock() {
        return clock;
    }
}
