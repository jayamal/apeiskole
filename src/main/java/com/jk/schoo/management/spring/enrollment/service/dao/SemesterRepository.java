package com.jk.schoo.management.spring.enrollment.service.dao;

import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jayamalk on 7/16/2018.
 */
@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    List<Semester> findAllByCourse(Course value);
}
