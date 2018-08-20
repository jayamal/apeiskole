package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.domain.FeeType;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
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
public class FeeView extends VerticalLayout {

    @Autowired
    private FeeGrid feeGrid;
    @Autowired
    private FeeTypeGrid feeTypeGrid;

    @Autowired
    private DiscountGrid discountGrid;

    public void init(){
        removeAll();
        setPadding(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        SplitLayout layout = new SplitLayout();
        layout.setOrientation(SplitLayout.Orientation.VERTICAL);
        feeGrid.init();
        feeTypeGrid.init();
        discountGrid.init();
        HorizontalLayout feeLayout = new HorizontalLayout();
        feeLayout.setMargin(Boolean.FALSE);
        feeLayout.setPadding(Boolean.FALSE);
        feeLayout.setSpacing(Boolean.FALSE);
        feeLayout.add(feeTypeGrid, feeGrid);
        layout.addToPrimary(feeLayout);
        layout.addToSecondary(discountGrid);
        add(layout);
        layout.setSizeFull();
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
        feeLayout.setWidth("100%");
        feeLayout.setHeight("100%");
    }

}
