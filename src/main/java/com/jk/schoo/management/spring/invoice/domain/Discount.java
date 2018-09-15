package com.jk.schoo.management.spring.invoice.domain;

import com.jk.schoo.management.spring.common.domain.AbstractEntity;
import com.jk.schoo.management.spring.customer.domain.Customer;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jayamalk on 9/4/2018.
 */

@Entity
public class Discount extends AbstractEntity {

    public static final String CODE = "code";
    public static final String DISCOUNT_PERCENTAGE = "discountPercentage";

    @NotNull
    private String code;
    @NotNull
    @Min(0)
    @Max(100)
    private Double discountPercentage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentTerm_id")
    @NotNull
    private PaymentTerm paymentTerm;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "discount_customers",
            joinColumns = {@JoinColumn(name = "discount_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    private Set<Customer> customers = new HashSet<>();

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public PaymentTerm getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(PaymentTerm paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + " (" + discountPercentage + "%)";
    }

}
