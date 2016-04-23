package com.jobvacancy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jobvacancy.domain.Company;
import com.jobvacancy.repository.CompanyRepository;
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
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkerResource {

    private final Logger log = LoggerFactory.getLogger(CompanyResource.class);

    @Inject
    private CompanyRepository companyRepository;

    @RequestMapping(value = "/workers",
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
}
