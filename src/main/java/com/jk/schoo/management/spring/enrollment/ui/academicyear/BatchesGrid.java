package com.jk.schoo.management.spring.enrollment.ui.academicyear;

import com.jk.schoo.management.spring.enrollment.domain.Batch;
import com.jk.schoo.management.spring.enrollment.domain.Semester;
import com.jk.schoo.management.spring.enrollment.service.dao.BatchRepository;
import com.jk.schoo.management.spring.enrollment.service.dao.SemesterRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jayamalk on 7/18/2018.
 */
@SpringComponent
@UIScope
public class BatchesGrid extends GridCrud<Batch> {

    private Semester semester;
    @Autowired
    private SemesterRepository semesterRepository;
    @Autowired
    private BatchRepository batchRepository;

    public BatchesGrid(){
        super(Batch.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(Batch.FIELD_NAME);
        getGrid().setColumns(Batch.FIELD_NAME);
        getGrid().getColumnByKey(Batch.FIELD_NAME).setHeader("Batch");

        setCrudListener(new CrudListener<Batch>() {
            @Override
            public Collection<Batch> findAll() {
                semester = semester != null ? semesterRepository.getOne(semester.getId()) : null;
                return semester != null ? semester.getBatches() : new ArrayList<Batch>();
            }

            @Override
            public Batch add(Batch batch) {
                batch.setSemester(semester);
                return batchRepository.save(batch);
            }

            @Override
            public Batch update(Batch batch) {
                return batchRepository.save(batch);
            }

            @Override
            public void delete(Batch batch) {
                semester.getBatches().remove(batch);
                semesterRepository.save(semester);
            }
        });
        setSizeFull();
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
        refreshGrid();
    }
}
