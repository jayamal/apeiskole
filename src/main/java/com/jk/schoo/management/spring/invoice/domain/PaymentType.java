package com.jk.schoo.management.spring.invoice.domain;

/**
 * Created by jayamalk on 9/4/2018.
 */
public enum PaymentType {

    CASH("Cash"),
    CHEQUE("Cheque");

    private String displayName;

    PaymentType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
