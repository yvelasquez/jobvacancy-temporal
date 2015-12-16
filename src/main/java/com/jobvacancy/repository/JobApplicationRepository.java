package com.jobvacancy.repository;

import com.jobvacancy.domain.JobApplication;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by nicopaez on 12/16/15.
 */
public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
}
