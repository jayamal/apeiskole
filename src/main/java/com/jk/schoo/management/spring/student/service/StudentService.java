package com.jk.schoo.management.spring.student.service;

import com.jk.schoo.management.spring.student.domain.Student;

import java.util.List;

/**
 * Created by jayamalk on 7/5/2018.
 */
public interface StudentService {

    /**
     * Get all students
     * @return
     */
    List<Student> getAll();

    /**
     * Get single student
     * @param id
     * @return
     */
    Student get(Long id);

    /**
     * Delete
     * @param student
     * @return
     */
    void delete(Student student);

    /**
     * Update
     * @param student
     * @return
     */
    void update(Student student);
}
