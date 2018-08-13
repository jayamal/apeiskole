package com.jk.schoo.management.spring.transaction.enumconstant;

/**
 * Created by jayamalk on 7/28/2018.
 */
public enum PaymentType {

    CASH("Cash"),
    CHEQUE("Cheque"),
    REFUND("Refund")
    ;

    private String name;

    PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
