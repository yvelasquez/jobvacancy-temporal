package com.jobvacancy.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "jobapplication")
public class JobApplication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "applicantname", nullable = false)
    private String applicatName;

    public String getApplicatName() {
        return applicatName;
    }

    public void setApplicatName(String applicatName) {
        this.applicatName = applicatName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @NotNull
    @Column(name = "applicantemail", nullable = false)
    private String applicantEmail;

    @ManyToOne
    private Offer offer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobApplication application = (JobApplication) o;

        if ( ! Objects.equals(id, application.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobApplication{" +
            "id=" + id +
            ", name='" + applicatName + "'" +
            ", email='" + applicantEmail + "'" +
            ", offer_id='" + offer.getId() + "'" +
            '}';
    }
}
