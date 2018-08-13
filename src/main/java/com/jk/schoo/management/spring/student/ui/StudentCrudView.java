package com.jk.schoo.management.spring.student.ui;

import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Calendar;
import java.util.Collection;

/**
 * Created by jayamalk on 7/20/2018.
 */
@SpringComponent
@UIScope
public class StudentCrudView extends VerticalLayout {

    @Autowired
    private StudentService studentService;

    public void init(){
        removeAll();
        GridCrud<Student> crudStudentGrid = new GridCrud<>(Student.class);
        crudStudentGrid.getCrudFormFactory().setUseBeanValidation(true);
        crudStudentGrid.getCrudFormFactory().setVisibleProperties(Student.FIELD_NAME, Student.FIELD_BRANCH);
        crudStudentGrid.getGrid().setColumns(Student.FIELD_ID, Student.FIELD_NAME, Student.FIELD_BRANCH, Student.FIELD_CREATE_DATE_TIME, Student.FIELD_LAST_UPDATED_DATE_TIME);
        add(crudStudentGrid);
        setSizeFull();
        crudStudentGrid.setSizeFull();

        crudStudentGrid.setCrudListener(new CrudListener<Student>() {
            @Override
            public Collection<Student> findAll() {
                return studentService.getAll();
            }

            @Override
            public Student add(Student student) {
                student.setCreateDateTime(Calendar.getInstance().getTime());
                studentService.update(student);
                return student;
            }

            @Override
            public Student update(Student student) {
                student.setLastUpdatedDateTime(Calendar.getInstance().getTime());
                studentService.update(student);
                return student;
            }

            @Override
            public void delete(Student student) {
                studentService.delete(student);
            }
        });
    }

}
