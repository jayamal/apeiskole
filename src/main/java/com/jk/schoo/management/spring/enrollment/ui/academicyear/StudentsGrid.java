package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.Batch;
import com.jk.schoo.management.spring.enrollment.service.dao.BatchRepository;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.dao.StudentRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by jayamalk on 7/18/2018.
 */
@SpringComponent
@UIScope
public class StudentsGrid extends GridCrud<Student> {

    private Batch batch;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private StudentRepository studentRepository;

    public StudentsGrid() {
        super(Student.class);
    }

    public void init() {
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(CrudOperation.ADD, Student.FIELD_ID);
        getCrudFormFactory().setVisibleProperties(CrudOperation.DELETE, Student.FIELD_ID);
        setDeletedMessage("Student removed from the batch");
        setUpdateOperationVisible(Boolean.FALSE);
        getGrid().setColumns(Student.FIELD_ID, Student.FIELD_NAME, Student.FIELD_BRANCH);
        getGrid().getColumnByKey(Student.FIELD_NAME).setHeader("Student");

        setCrudListener(new CrudListener<Student>() {
            @Override
            public Collection<Student> findAll() {
                batch = batch != null ? batchRepository.getOne(batch.getId()) : null;
                return batch != null ? batch.getStudents() : new ArrayList<Student>();
            }

            @Override
            public Student add(Student student) {
                try {
                    Student studentFetched = studentRepository.getOne(student.getId());
                    studentFetched.getBatches().add(batch);
                    if(batch.getStudents() == null){
                        batch.setStudents(new HashSet<>());
                    }
                    batch.getStudents().add(studentFetched);
                    Student studentAdded = studentRepository.save(studentFetched);
                    return studentAdded;
                }catch (Exception ex){
                    ex.printStackTrace();
                    return null;
                }
            }

            @Override
            public Student update(Student student) {
                return null;
            }

            @Override
            public void delete(Student student) {
                batch.getStudents().remove(student);
                batchRepository.save(batch);
            }
        });
        setSizeFull();
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
        refreshGrid();
    }
}