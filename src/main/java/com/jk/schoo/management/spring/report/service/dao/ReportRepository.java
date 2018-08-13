package com.jk.schoo.management.spring.report.service.dao;

import com.jk.schoo.management.spring.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jayamalk on 7/9/2018.
 */
@Repository
public interface ReportRepository   extends JpaRepository<Report, Integer> {
}
