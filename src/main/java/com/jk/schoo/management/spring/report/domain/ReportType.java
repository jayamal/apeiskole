package com.jk.schoo.management.spring.report.domain;

/**
 * Created by jayamalk on 7/9/2018.
 */
public enum ReportType {

    MANUAL("Manual"),
    SCHEDULED("Scheduled");

    private String displayName;

    ReportType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
