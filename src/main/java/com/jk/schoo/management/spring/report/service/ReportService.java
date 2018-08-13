package com.jk.schoo.management.spring.report.service;

import com.jk.schoo.management.spring.report.domain.Report;

import java.util.List;

/**
 * Created by jayamalk on 7/9/2018.
 */
public interface ReportService {

    /**
     * Get All
     * @return
     */
    public List<Report> getAll();

    /**
     * Update
     * @param report
     */
    public void update(Report report);
}
