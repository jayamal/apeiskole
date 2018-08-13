package com.jk.schoo.management.spring;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Batch;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import com.jk.schoo.management.spring.enrollment.service.EnrollmentService;
import com.jk.schoo.management.spring.enrollment.service.dao.AcademicYearRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.BatchRepository;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.dao.StudentRepository;
import com.jk.schoo.management.spring.transaction.domain.FeeType;
import com.jk.schoo.management.spring.transaction.service.dao.FeeTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application {

    @PersistenceContext
    private EntityManager entityManager;
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(BatchRepository batchRepository, StudentRepository repository, EnrollmentService enrollmentService, AcademicYearRepository academicYearRepository, FeeTypeRepository feeTypeRepository) {
        return (args) -> {
            for(int i = 0; i < 50 ; i++){
                Student student = new Student();
                student.setName("Name " + i);
                student.setBranch(i % 2 == 0 ? "Kandy" : "Digana");
                student.setCreateDateTime(Calendar.getInstance().getTime());
                repository.save(student);
            }
            //create academic year
            AcademicYear academicYear = new AcademicYear();
            academicYear.setName("2017/2018");
            //Courses
            List<Course> courses = new ArrayList<Course>();
            Course grade1 = new Course();
            grade1.setName("Grade 11");
            grade1.setAcademicYear(academicYear);
            Course grade2 = new Course();
            grade2.setName("Grade 12");
            grade2.setAcademicYear(academicYear);
            courses.add(grade1);
            courses.add(grade2);
            academicYear.setCourses(courses);
            //Semesters
            List<Semester> semesters = new ArrayList<Semester>();
            Semester semester = new Semester();
            semester.setName("Semester 1");
            semesters.add(semester);
            semester.setCourse(grade1);
            grade1.setSemesters(semesters);
            //Batches
            List<Batch> batches = new ArrayList<Batch>();
            Batch batch = new Batch();
            batch.setName("Class A");
            batches.add(batch);



            semester.setBatches(batches);
            batch.setSemester(semester);


            enrollmentService.createAcademicYear(academicYear);


            Set<Batch> batchesStudent = new HashSet<>();
            Batch batch1 = batchRepository.getOne(54L);
            batchesStudent.add(batch1);

            Student st1 = repository.getOne(6L);
            Student st2 = repository.getOne(7L);
            Student st3 = repository.getOne(8L);
            st1.setBatches(batchesStudent);
            st2.setBatches(batchesStudent);
            st3.setBatches(batchesStudent);
            Set<Student> students = new HashSet<>();
            students.add(st1);
            students.add(st2);
            students.add(st3);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            repository.saveAll(students);

            Batch batch2 = batchRepository.getOne(54L);
            batch2.setStudents(students);
            batchRepository.save(batch2);


            Batch batch3 = batchRepository.getOne(54L);
            batch3.setStudents(students);
            batch3.getStudents().remove(st1);
            batchRepository.save(batch3);

            FeeType feeType = new FeeType();
            feeType.setName("Term Fee");
            feeTypeRepository.save(feeType);

        };
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
