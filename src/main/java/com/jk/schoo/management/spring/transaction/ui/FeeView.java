package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.domain.FeeType;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jayamalk on 7/23/2018.
 */
@SpringComponent
@UIScope
public class FeeView extends HorizontalLayout {

    @Autowired
    private FeeGrid feeGrid;
    @Autowired
    private FeeTypeGrid feeTypeGrid;

    public void init(){
        feeGrid.init();
        feeTypeGrid.init();
        add(feeTypeGrid, feeGrid);
        setSizeFull();
        feeTypeGrid.getGrid().addSelectionListener(new SelectionListener<Grid<FeeType>, FeeType>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<FeeType>, FeeType> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    feeGrid.setFeeType(selectionEvent.getFirstSelectedItem().get());
                }else{
                    feeGrid.setFeeType(null);
                }
            }
        });
    }

}
