package com.jobvacancy.web.rest.dto;

/**
 * Created by nicopaez on 4/26/16.
 */
public class BillingDTO {

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String companyName;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private Integer amount;
}
