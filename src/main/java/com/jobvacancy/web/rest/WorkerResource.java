package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.Company;
import com.jobvacancy.repository.CompanyRepository;
import com.jobvacancy.web.rest.dto.BillingDTO;
import com.jobvacancy.web.rest.dto.WorkerDTO;
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
    public ResponseEntity<WorkerDTO> runReputation() {
        log.debug("Running workers");
        WorkerDTO dto = new WorkerDTO();
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @RequestMapping(value = "/billing",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    public List<BillingDTO> runBilling() {
        log.debug("Running workers");
        List<BillingDTO> result =  new ArrayList<BillingDTO>();
        return result;
    }
}
