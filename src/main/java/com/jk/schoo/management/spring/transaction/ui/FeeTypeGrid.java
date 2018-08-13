package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.domain.FeeType;
import com.jk.schoo.management.spring.transaction.service.dao.FeeTypeRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.Collection;
import java.util.Set;

/**
 * Created by jayamalk on 7/23/2018.
 */
@SpringComponent
@UIScope
public class FeeTypeGrid  extends GridCrud<FeeType> {

    @Autowired
    private FeeTypeRepository feeTypeRepository;
    @Autowired
    private EntityManager entityManager;

    public FeeTypeGrid() {
        super(FeeType.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        getCrudFormFactory().setVisibleProperties(FeeType.FIELD_NAME);
        getGrid().setColumns(FeeType.FIELD_NAME);
        setSizeFull();
        Set<EntityType<?>> entities = entityManager.getEntityManagerFactory().getMetamodel().getEntities();

        setCrudListener(new CrudListener<FeeType>() {
            @Override
            public Collection<FeeType> findAll() {
                return feeTypeRepository.findAll();
            }

            @Override
            public FeeType add(FeeType feeType) {
                return feeTypeRepository.save(feeType);
            }

            @Override
            public FeeType update(FeeType feeType) {
                return feeTypeRepository.save(feeType);
            }

            @Override
            public void delete(FeeType feeType) {
                feeTypeRepository.delete(feeType);
            }
        });
    }

}