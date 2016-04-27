package com.jobvacancy.service;

import com.jobvacancy.domain.Company;
import com.jobvacancy.domain.Subscription;
import com.jobvacancy.repository.CompanyRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nicopaez on 4/23/16.
 */
@Component
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

    @Inject
    private CompanyRepository companyRepository;

    public HashMap<Company, Integer> run() {
        HashMap<Company, Integer> result = new HashMap<>();
        for (Company company: companyRepository.findAll()) {
            int amount = this.calculateFor(company);
            result.put(company,amount);
        }
        return result;
    }
}
