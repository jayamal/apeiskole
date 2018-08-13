package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import com.jk.schoo.management.spring.enrollment.service.dao.CourseRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.SemesterRepository;
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
public class SemestersGrid extends GridCrud<Semester> {

    private Course course;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemesterRepository semesterRepository;

    public SemestersGrid(){
        super(Semester.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(Course.FIELD_NAME);
        getGrid().setColumns(Course.FIELD_NAME);
        getGrid().getColumnByKey(Course.FIELD_NAME).setHeader("Semester");

        setCrudListener(new CrudListener<Semester>() {
            @Override
            public Collection<Semester> findAll() {
                course = course != null ? courseRepository.getOne(course.getId()) : null;
                return course != null ? course.getSemesters() : new ArrayList<Semester>();
            }

            @Override
            public Semester add(Semester semester) {
                semester.setCourse(course);
                return semesterRepository.save(semester);
            }

            @Override
            public Semester update(Semester semester) {
                return semesterRepository.save(semester);
            }

            @Override
            public void delete(Semester semester) {
                course.getSemesters().remove(semester);
                courseRepository.save(course);
            }
        });
        setSizeFull();
    }

    public void setCourse(Course course) {
        this.course = course;
        refreshGrid();
    }
}