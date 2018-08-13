package com.jk.schoo.management.spring.report.service.impl;

import com.jk.schoo.management.spring.report.domain.Report;
import com.jk.schoo.management.spring.report.service.ReportService;
import com.jk.schoo.management.spring.report.service.dao.ReportRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Created by jayamalk on 7/9/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ReportServiceImpl implements ReportService {

    private ReportRepository reportRepository;

    public ReportServiceImpl(@Autowired ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> getAll() {
        return this.reportRepository.findAll();
    }

    @Override
    public void update(Report report) {
        this.reportRepository.save(report);
    }
}
