package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.service.dao.AcademicYearRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Collection;

/**
 * Created by jayamalk on 7/18/2018.
 */
@SpringComponent
@UIScope
public class AcademicYearGrid extends GridCrud<AcademicYear> {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    public AcademicYearGrid() {
        super(AcademicYear.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(AcademicYear.FIELD_NAME);
        getGrid().setColumns(AcademicYear.FIELD_NAME);
        getGrid().getColumnByKey(AcademicYear.FIELD_NAME).setHeader("Academic Year");

        setCrudListener(new CrudListener<AcademicYear>() {
            @Override
            public Collection<AcademicYear> findAll() {
                return academicYearRepository.findAll();
            }

            @Override
            public AcademicYear add(AcademicYear academicYear) {
                return academicYearRepository.save(academicYear);
            }

            @Override
            public AcademicYear update(AcademicYear academicYear) {
                return academicYearRepository.save(academicYear);
            }

            @Override
            public void delete(AcademicYear academicYear) {
                academicYearRepository.delete(academicYear);
            }
        });

    }

}
