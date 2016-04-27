package com.jobvacancy.service;

import com.jobvacancy.Application;
import com.jobvacancy.domain.Company;
import com.jobvacancy.repository.CompanyRepository;
import org.boon.di.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by nicopaez on 4/27/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class BillingServiceTest {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    BillingService billingService;

    @Test
    public void shouldCalculateForAllCompanies() {
        Company company = companyRepository.findOne(1L);
        HashMap<Company, Integer>  result = this.billingService.run();
        assertEquals(1, result.size());
        assertTrue(result.containsKey(company));
    }
}
