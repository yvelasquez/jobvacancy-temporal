package com.jobvacancy.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

/**
 * Created by nicopaez on 4/24/16.
 */
public class CompanyTest {

    @Test
    public void isCreatedWithBasicSubscription() {

        Company company = new Company();
        assertEquals(company.getSubscription().getClass(), (BasicSubscription.class));

    }
}
