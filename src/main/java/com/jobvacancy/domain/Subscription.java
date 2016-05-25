package com.jobvacancy.domain;

import javax.persistence.*;

public class Subscription {

    public int calculateAmountToBill() {
        return 0;
    }

    public enum Type {
        BASIC,
        STANDARD,
        ONDEMAND,
        ADVANCED
    }

}


