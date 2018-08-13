package com.jk.schoo.management.spring.transaction.enumconstant;

/**
 * Created by jayamalk on 7/28/2018.
 */
public enum TransactionStatus {

    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    REFUNDED("Refunded")

            ;

    private String name;

    TransactionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
