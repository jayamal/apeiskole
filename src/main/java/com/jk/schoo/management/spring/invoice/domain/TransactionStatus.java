package com.jk.schoo.management.spring.invoice.domain;

/**
 * Created by jayamalk on 9/4/2018.
 */
public enum TransactionStatus {

    CLOSED("Closed"),
    OPEN("Open"),
    PARTIAL("Partial"),
    PAID("Paid");

    private String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
