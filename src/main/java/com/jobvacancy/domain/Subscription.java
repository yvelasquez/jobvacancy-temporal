package com.jobvacancy.domain;

import javax.persistence.*;

public abstract class Subscription {

    public abstract int calculateAmountToBill();

    public enum Type {
        BASIC,
        STANDARD,
        ONDEMAND,
        ADVANCED
    }

    public static Subscription createInstanceFor(Company company) {
        switch (company.getSubscriptionType()) {
            case BASIC:
                return new BasicSubscription();
            case ONDEMAND:
                return new OnDemandSubscription(company);
            case STANDARD:
                return new StandardSubscription(company);
            case ADVANCED:
                return new AdvancedSubscription(company);

        }
        throw new InvalidSubscriptionException();
    }
}


