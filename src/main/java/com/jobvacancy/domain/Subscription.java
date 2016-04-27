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

}


