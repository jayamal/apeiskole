package com.jk.schoo.management.spring.enrollment.service.dao;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jayamalk on 7/16/2018.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByAcademicYear(AcademicYear value);

}
