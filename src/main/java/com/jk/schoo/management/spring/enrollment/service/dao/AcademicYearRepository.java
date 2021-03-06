package com.jk.schoo.management.spring.enrollment.service.dao;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jayamalk on 7/16/2018.
 */
@Repository
public interface AcademicYearRepository  extends JpaRepository<AcademicYear, Long> {
}
