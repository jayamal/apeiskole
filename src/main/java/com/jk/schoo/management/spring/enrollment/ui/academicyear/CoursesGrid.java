package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.service.dao.AcademicYearRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.CourseRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jayamalk on 7/18/2018.
 */
@SpringComponent
@UIScope
public class CoursesGrid extends GridCrud<Course> {

    private AcademicYear academicYear;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AcademicYearRepository academicYearRepository;

    public CoursesGrid(){
        super(Course.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(Course.FIELD_NAME);
        getGrid().setColumns(Course.FIELD_NAME);
        getGrid().getColumnByKey(Course.FIELD_NAME).setHeader("Course");

        setCrudListener(new CrudListener<Course>() {
            @Override
            public Collection<Course> findAll() {
                academicYear = academicYear != null ? academicYearRepository.getOne(academicYear.getId()) : null;
                return academicYear != null ? academicYear.getCourses() : new ArrayList<Course>();
            }

            @Override
            public Course add(Course course) {
                course.setAcademicYear(academicYear);
                return courseRepository.save(course);
            }

            @Override
            public Course update(Course course) {
                return courseRepository.save(course);
            }

            @Override
            public void delete(Course course) {
                academicYear.getCourses().remove(course);
                academicYearRepository.save(academicYear);
            }
        });
        setSizeFull();
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
        refreshGrid();
    }
}
