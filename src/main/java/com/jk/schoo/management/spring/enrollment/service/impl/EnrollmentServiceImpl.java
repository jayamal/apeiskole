package com.jk.schoo.management.spring.enrollment.service.impl;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.service.EnrollmentService;
import com.jk.schoo.management.spring.enrollment.service.dao.AcademicYearRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Created by jayamalk on 7/16/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    @Override
    public void createAcademicYear(AcademicYear academicYear) {
        academicYearRepository.save(academicYear);
    }

    @Override
    public List<AcademicYear> getAcademicYears() {
        return academicYearRepository.findAll();
    }

    public AcademicYearRepository getAcademicYearRepository() {
        return academicYearRepository;
    }

    public void setAcademicYearRepository(AcademicYearRepository academicYearRepository) {
        this.academicYearRepository = academicYearRepository;
    }
}
