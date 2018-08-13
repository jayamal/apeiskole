package com.jk.schoo.management.spring.transaction.enumconstant;

public enum AmountBreakdown {

    DISCOUNT("Discount"),
    PENALTY("Penalty"),
    AMOUNT("Amount"),
    OUTSTANDING("Outstanding"),
    PAID("Paid"),
    ;

    private String name;

    AmountBreakdown(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}