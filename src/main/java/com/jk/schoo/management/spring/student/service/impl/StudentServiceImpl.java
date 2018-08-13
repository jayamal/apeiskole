package com.jk.schoo.management.spring.student.service.impl;

import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.jk.schoo.management.spring.student.service.dao.StudentRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Created by jayamalk on 7/5/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(@Autowired StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student get(Long id) {
        return this.studentRepository.getOne(id);
    }

    @Override
    public void delete(Student student) {
        this.studentRepository.delete(student);
    }

    @Override
    public void update(Student student) {
        this.studentRepository.save(student);
    }
}
