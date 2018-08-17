package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.domain.Discount;
import com.jk.schoo.management.spring.transaction.service.dao.DiscountRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.Collection;

/**
 * Created by jayamalk on 7/23/2018.
 */
@SpringComponent
@UIScope
public class DiscountGrid extends GridCrud<Discount> {

    @Autowired
    private DiscountRepository discountRepository;

    public DiscountGrid() {
        super(Discount.class);
    }

    public void init(){
        getCrudFormFactory().setUseBeanValidation(true);
        setDeleteOperationVisible(Boolean.FALSE);
        getCrudFormFactory().setVisibleProperties(Discount.FIELD_CODE, Discount.FIELD_DESCRIPTION, Discount.FIELD_PERCENTAGE);
        getGrid().setColumns(Discount.FIELD_CODE, Discount.FIELD_DESCRIPTION, Discount.FIELD_PERCENTAGE);
        setSizeFull();
        setCrudListener(new CrudListener<Discount>() {
            @Override
            public Collection<Discount> findAll() {
                return discountRepository.findAll();
            }

            @Override
            public Discount add(Discount discount) {
                return discountRepository.save(discount);
            }

            @Override
            public Discount update(Discount discount) {
                return discountRepository.save(discount);
            }

            @Override
            public void delete(Discount feeType) {
                discountRepository.delete(feeType);
            }
        });
    }

}