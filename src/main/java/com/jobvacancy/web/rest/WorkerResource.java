package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.Company;
import com.jobvacancy.repository.CompanyRepository;
import com.jobvacancy.service.BillingService;
import com.jobvacancy.web.rest.dto.BillingDTO;
import com.jobvacancy.web.rest.dto.WorkerDTO;
import com.jobvacancy.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class WorkerResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    private CompanyRepository companyRepository;

    @RequestMapping(value = "/reputation",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public ResponseEntity<WorkerDTO> run() {
        log.debug("Running workers");
        int updatedCompanies = 0;
        WorkerDTO dto = new WorkerDTO();
        List<Company> companies = companyRepository.findAll();
        for (Company c :companies) {
            c.updateReputation();
            companyRepository.save(c);
            updatedCompanies++;
        }
        dto.setUpdatedCompanies(updatedCompanies);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @Inject
    private BillingService billingService;

    @RequestMapping(value = "/billing",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public List<BillingDTO> runBilling() {
        log.debug("Running workers");
        List<BillingDTO> result =  new ArrayList<BillingDTO>();
        HashMap<Company, Integer> billings = billingService.run();
        for(Map.Entry<Company, Integer> entry : billings.entrySet()) {
            BillingDTO dto = new BillingDTO();
            dto.setAmount(entry.getValue());
            dto.setCompanyName(entry.getKey().getName());
            result.add(dto);
        }
        return result;
    }
}
