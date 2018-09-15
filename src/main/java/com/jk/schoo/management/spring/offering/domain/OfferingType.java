package com.jk.schoo.management.spring.offering.domain;

/**
 * Created by jayamalk on 9/4/2018.
 */
public enum OfferingType {

    NON_INVENTORY("Non-Inventory"),
    SERVICE("Service");
    //BUNDLE("Bundle"); Yet to implement

    private String displayName;

    OfferingType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
