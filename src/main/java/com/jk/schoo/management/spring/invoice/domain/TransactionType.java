package com.jk.schoo.management.spring.invoice.domain;

/**
 * Created by jayamalk on 9/4/2018.
 */
public enum TransactionType {

    PAYMENT("Payment"),
    INVOICE("Invoice"),
    CREDIT_MEMO("Credit Memo"),
    CHARGE("Charge");

    private String displayName;

    TransactionType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
