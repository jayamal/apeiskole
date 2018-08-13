package com.jk.schoo.management.spring.enrollment.service;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;

import java.util.List;

/**
 * Created by jayamalk on 7/16/2018.
 */
public interface EnrollmentService {

    /**
     * Create Academic Year
     * @param academicYear
     */
    public void createAcademicYear(AcademicYear academicYear);

    public List<AcademicYear> getAcademicYears();
}
