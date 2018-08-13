package com.jk.schoo.management.spring.report.domain;

/**
 * Created by jayamalk on 7/9/2018.
 */
public enum ReportStatus {

    PENDING("Pending"),
    GENERATED("Generated");

    private String displayName;

    ReportStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
