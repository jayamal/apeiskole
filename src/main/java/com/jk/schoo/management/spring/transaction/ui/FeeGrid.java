package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import com.jk.schoo.management.spring.enrollment.service.dao.AcademicYearRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.CourseRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.SemesterRepository;
import com.jk.schoo.management.spring.transaction.domain.Fee;
import com.jk.schoo.management.spring.transaction.domain.FeeType;
import com.jk.schoo.management.spring.transaction.service.dao.FeeRepository;
import com.jk.schoo.management.spring.transaction.service.dao.FeeTypeRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import javax.persistence.EntityManager;
import java.util.Collection;

/**
 * Created by jayamalk on 7/23/2018.
 */
@SpringComponent
@UIScope
public class FeeGrid extends GridCrud<Fee> {

    private FeeType feeType;

    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private FeeTypeRepository feeTypeRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AcademicYearRepository academicYearRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SemesterRepository semesterRepository;

    public FeeGrid() {
        super(Fee.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(Fee.FIELD_ACADEMIC_YEAR,  Fee.FIELD_COURSE,  Fee.FIELD_SEMESTER, Fee.FIELD_AMOUNT);
        ComboBox<Course> courseComboBox = new ComboBox<>();
        ComboBox<Semester> semesterComboBox = new ComboBox<>();
        ComboBox<AcademicYear> academicYearComboBox = new ComboBox<>();
        getCrudFormFactory().setFieldProvider(Fee.FIELD_ACADEMIC_YEAR, new AbstractListingProvider<ComboBox<AcademicYear>, AcademicYear>(academicYearRepository.findAll()) {
            @Override
            protected ComboBox<AcademicYear> buildAbstractListing() {
                academicYearComboBox.setItems(items);
                academicYearComboBox.setItemLabelGenerator(new ItemLabelGenerator<AcademicYear>() {
                    @Override
                    public String apply(AcademicYear academicYear) {
                        return academicYear.getName();
                    }
                });
                academicYearComboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<AcademicYear>, AcademicYear>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<AcademicYear>, AcademicYear> comboBoxAcademicYearComponentValueChangeEvent) {
                        courseComboBox.setItems(courseRepository.findAllByAcademicYear(comboBoxAcademicYearComponentValueChangeEvent.getValue()));
                    }
                });
                return academicYearComboBox;
            }
        });
        getCrudFormFactory().setFieldProvider(Fee.FIELD_COURSE, new AbstractListingProvider<ComboBox<Course>, Course>(courseRepository.findAll()) {
            @Override
            protected ComboBox<Course> buildAbstractListing() {
                courseComboBox.setItems(items);
                courseComboBox.setItemLabelGenerator(new ItemLabelGenerator<Course>() {
                    @Override
                    public String apply(Course course) {
                        return course.getName();
                    }
                });
                courseComboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Course>, Course>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<Course>, Course> comboBoxCourseComponentValueChangeEvent) {
                        semesterComboBox.setItems(semesterRepository.findAllByCourse(comboBoxCourseComponentValueChangeEvent.getValue()));
                    }
                });
                return courseComboBox;
            }
        });
        getCrudFormFactory().setFieldProvider(Fee.FIELD_SEMESTER, new AbstractListingProvider<ComboBox<Semester>, Semester>(semesterRepository.findAll()) {
            @Override
            protected ComboBox<Semester> buildAbstractListing() {
                semesterComboBox.setItems(items);
                semesterComboBox.setItemLabelGenerator(new ItemLabelGenerator<Semester>() {
                    @Override
                    public String apply(Semester semester) {
                        return semester.getName();
                    }
                });
                return semesterComboBox;
            }
        });

        getGrid().setColumns();
        getGrid().addColumn(fee->fee.getFeeType().getName()).setHeader("Type");
        getGrid().addColumn(fee->fee.getAcademicYear() != null ? fee.getAcademicYear().getName() : "").setHeader("Academic Year");
        getGrid().addColumn(fee->fee.getCourse() != null ? fee.getCourse().getName() : "").setHeader("Course");
        getGrid().addColumn(fee->fee.getSemester() != null ? fee.getSemester().getName() : "").setHeader("Semester");
        getGrid().addColumn(fee->fee.getAmount()).setHeader("Amount");
        setSizeFull();

        setCrudListener(new CrudListener<Fee>() {
            @Override
            public Collection<Fee> findAll() {
                if(feeType == null){
                    return feeRepository.findAll();
                }
                return feeRepository.findByFeeType(feeType);
            }

            @Override
            public Fee add(Fee fee) {
                fee.setFeeType(feeType);
                return feeRepository.save(fee);
            }

            @Override
            public Fee update(Fee fee) {
                return feeRepository.save(fee);
            }

            @Override
            public void delete(Fee fee) {
                feeRepository.delete(fee);
            }
        });

    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
        refreshGrid();
    }
}