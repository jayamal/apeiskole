package com.jk.schoo.management.spring.customer.domain;

import com.jk.schoo.management.spring.invoice.domain.Discount;

/**
 * Created by jayamalk on 9/4/2018.
 */
public class CustomerDiscount {

    public static final String DISCOUNT = "discount";

    private Discount discount;

    public CustomerDiscount() {
    }

    public CustomerDiscount(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}
