package com.jk.schoo.management.spring.invoice.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by jayamalk on 9/4/2018.
 */

@Entity
public class Penalty extends AbstractEntity {

    public static final String CODE = "code";
    public static final String PENALTY_PERCENTAGE = "penaltyPercentage";
    public static final String DUE_DAYS = "dueDays";

    @NotNull
    private String code;
    @NotNull
    @Min(0)
    @Max(100)
    private Double penaltyPercentage;
    @NotNull
    @Min(0)
    private Integer dueDays;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentTerm_id")
    @NotNull
    private PaymentTerm paymentTerm;

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Integer getDueDays() {
        return dueDays;
    }

    public void setDueDays(Integer dueDays) {
        this.dueDays = dueDays;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPenaltyPercentage() {
        return penaltyPercentage;
    }

    public void setPenaltyPercentage(Double penaltyPercentage) {
        this.penaltyPercentage = penaltyPercentage;
    }

    @Override
    public String toString() {
        return code + " (" + penaltyPercentage + "%)";
    }

}
