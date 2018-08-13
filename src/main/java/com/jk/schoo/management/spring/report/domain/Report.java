package com.jk.schoo.management.spring.report.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jayamalk on 7/9/2018.
 */
@Entity
public class Report {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String initiator;
    @Enumerated(EnumType.STRING)
    private ReportType type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date initiatedDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedDateTime;
    private String path;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public Date getInitiatedDateTime() {
        return initiatedDateTime;
    }

    public void setInitiatedDateTime(Date initiatedDateTime) {
        this.initiatedDateTime = initiatedDateTime;
    }

    public Date getGeneratedDateTime() {
        return generatedDateTime;
    }

    public void setGeneratedDateTime(Date generatedDateTime) {
        this.generatedDateTime = generatedDateTime;
    }
}
