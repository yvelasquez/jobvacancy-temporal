package com.jobvacancy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Month;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reputation")
    private Integer reputation;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<Offer> offerss = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @Column(name = "contact_email")
    private String contactEmail;

    public Subscription.Type getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(Subscription.Type subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="subscription_type")
    private Subscription.Type subscriptionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<Offer> getOfferss() {
        return offerss;
    }

    public void setOfferss(Set<Offer> offers) {
        this.offerss = offers;
    }

    public Company() {
        this.subscriptionType = Subscription.Type.BASIC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        if ( ! Objects.equals(id, company.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", reputation='" + reputation + "'" +
            ", registrationDate='" + registrationDate + "'" +
            '}';
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void updateReputation() {
        if(this.offerss.size() == 0) {
            this.reputation = 0;
            return;
        }
        ZonedDateTime aMonthAgo = ZonedDateTime.now().minus(Period.ofMonths(1));
        int recentOffersCount = 0;
        for (Offer o : this.offerss) {
            if (o.getCreatedDate().isAfter(aMonthAgo)) {
                recentOffersCount++;
            }
        }
        this.reputation = recentOffersCount;
    }

    @Transient
    @JsonIgnore
    public Subscription getSubscription() {

        return Subscription.createInstanceFor(this);

    }

}
