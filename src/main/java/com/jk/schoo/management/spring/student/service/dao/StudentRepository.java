package com.jk.schoo.management.spring.student.service.dao;

import com.jk.schoo.management.spring.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jayamalk on 7/6/2018.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
