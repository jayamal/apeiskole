package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.AcademicYear;
import com.jk.schoo.management.spring.enrollment.domain.Batch;
import com.jk.schoo.management.spring.enrollment.domain.Course;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import com.jk.schoo.management.spring.enrollment.service.EnrollmentService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jayamalk on 7/18/2018.
 */
@SpringComponent
@UIScope
public class AcademicYearView extends HorizontalLayout{

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private AcademicYearGrid academicYearGrid;
    @Autowired
    private CoursesGrid coursesGrid;
    @Autowired
    private SemestersGrid semestersGrid;
    @Autowired
    private BatchesGrid batchesGrid;
    @Autowired
    private StudentsGrid studentsGrid;

    public void init() {
        removeAll();

        academicYearGrid.init();
        coursesGrid.init();
        semestersGrid.init();
        batchesGrid.init();
        studentsGrid.init();

        add(academicYearGrid);
        add(coursesGrid);
        add(semestersGrid);
        add(batchesGrid);
        add(studentsGrid);

        setSizeFull();

        academicYearGrid.getGrid().addSelectionListener(new SelectionListener<Grid<AcademicYear>, AcademicYear>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<AcademicYear>, AcademicYear> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    coursesGrid.setAcademicYear(selectionEvent.getFirstSelectedItem().get());
                }else{
                    coursesGrid.setAcademicYear(null);
                }
            }
        });
        coursesGrid.getGrid().addSelectionListener(new SelectionListener<Grid<Course>, Course>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<Course>, Course> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    semestersGrid.setCourse(selectionEvent.getFirstSelectedItem().get());
                }else{
                    semestersGrid.setCourse(null);
                }
            }
        });
        semestersGrid.getGrid().addSelectionListener(new SelectionListener<Grid<Semester>, Semester>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<Semester>, Semester> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    batchesGrid.setSemester(selectionEvent.getFirstSelectedItem().get());
                }else{
                    batchesGrid.setSemester(null);
                }
            }
        });
        batchesGrid.getGrid().addSelectionListener(new SelectionListener<Grid<Batch>, Batch>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<Batch>, Batch> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    studentsGrid.setBatch(selectionEvent.getFirstSelectedItem().get());
                }else{
                    studentsGrid.setBatch(null);
                }
            }
        });
    }

}
