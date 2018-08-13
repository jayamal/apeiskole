package com.jk.schoo.management.spring.transaction.domain;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by jayamalk on 7/23/2018.
 */
@Entity
public class Fee implements Reference{

    public static final String FIELD_ID = "id";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_TYPE = "feeType";
    public static final String FIELD_ACADEMIC_YEAR = "academicYear";
    public static final String FIELD_COURSE = "course";
    public static final String FIELD_SEMESTER = "semester";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Double amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feeType_id")
    @NotNull
    private FeeType feeType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="academicYear_id")
    @NotNull
    private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    @NotNull
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="semester_id")
    @NotNull
    private Semester semester;

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fee fee = (Fee) o;

        return getId() != null ? getId().equals(fee.getId()) : fee.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String getReferenceId() {
        return String.valueOf(this.id);
    }

    @Override
    public String getReferenceIdDisplayName() {
        return "[ " + this.academicYear.getName() + " - " + this.course.getName() + " - " + this.semester.getName() + " ] " + this.feeType.getName();
    }

    @Override
    public String getReferenceType() {
        return "fee";
    }

    @Override
    public String getReferenceTypeDisplayName() {
        return "Fee";
    }
}
