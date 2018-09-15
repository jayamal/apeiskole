package com.jk.schoo.management.spring.invoice.view;

import com.jk.schoo.management.spring.MainView;
import com.jk.schoo.management.spring.invoice.domain.Discount;
import com.jk.schoo.management.spring.invoice.domain.PaymentTerm;
import com.jk.schoo.management.spring.invoice.domain.Penalty;
import com.jk.schoo.management.spring.invoice.service.DiscountRepository;
import com.jk.schoo.management.spring.invoice.service.PaymentTermRepository;
import com.jk.schoo.management.spring.invoice.service.PenaltyRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by jayamalk on 9/5/2018.
 */
@Route(value = "payment_term", layout = MainView.class)
@UIScope
public class PaymentTermView extends VerticalLayout {

    private PaymentTerm paymentTerm;
    private static final Double SPLITTER_DEFAULT_POSITION = 60.0;

    @Autowired
    public PaymentTermView(PaymentTermRepository paymentTermRepository, DiscountRepository discountRepository, PenaltyRepository penaltyRepository) {
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        GridCrud<PaymentTerm> paymentTermGrid = new GridCrud<>(PaymentTerm.class);
        paymentTermGrid.getCrudFormFactory().setVisibleProperties(
                PaymentTerm.NAME
        );
        paymentTermGrid.getGrid().setColumns(
                PaymentTerm.ID,
                PaymentTerm.NAME
        );
        GridCrud<Discount> discountGrid = new GridCrud<>(Discount.class);
        discountGrid.getCrudFormFactory().setVisibleProperties(
                Discount.CODE,
                Discount.DISCOUNT_PERCENTAGE
        );
        discountGrid.getGrid().setColumns(
                PaymentTerm.ID,
                Discount.CODE,
                Discount.DISCOUNT_PERCENTAGE
        );
        GridCrud<Penalty> penaltyGrid = new GridCrud<>(Penalty.class);
        penaltyGrid.getCrudFormFactory().setVisibleProperties(
                Penalty.CODE,
                Penalty.PENALTY_PERCENTAGE,
                Penalty.DUE_DAYS
        );
        penaltyGrid.getGrid().setColumns(
                Penalty.ID,
                Penalty.CODE,
                Penalty.PENALTY_PERCENTAGE,
                Penalty.DUE_DAYS
        );
        discountGrid.getCrudFormFactory().setUseBeanValidation(true);
        penaltyGrid.getCrudFormFactory().setUseBeanValidation(true);
        paymentTermGrid.getCrudFormFactory().setUseBeanValidation(true);
        setSizeFull();
        paymentTermGrid.setSizeFull();
        discountGrid.setSizeFull();
        penaltyGrid.setSizeFull();
        //sub splitter
        SplitLayout splitLayoutSub = new SplitLayout();
        splitLayoutSub.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        splitLayoutSub.setSizeFull();
        splitLayoutSub.addToPrimary(discountGrid);
        splitLayoutSub.addToSecondary(penaltyGrid);
        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.add(splitLayoutSub);
        detailsLayout.setSizeFull();
        //main splitter
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(paymentTermGrid);
        splitLayout.addToSecondary(detailsLayout);
        splitLayout.setSplitterPosition(100);
        add(splitLayout);
        paymentTermGrid.setCrudListener(new CrudListener<PaymentTerm>() {
            @Override
            public Collection<PaymentTerm> findAll() {
                return paymentTermRepository.findAll();
            }

            @Override
            public PaymentTerm add(PaymentTerm paymentTerm) {
                return paymentTermRepository.save(paymentTerm);
            }

            @Override
            public PaymentTerm update(PaymentTerm paymentTerm) {
                return paymentTermRepository.save(paymentTerm);
            }

            @Override
            public void delete(PaymentTerm paymentTerm) {
                paymentTermRepository.delete(paymentTerm);
            }
        });
        discountGrid.setCrudListener(new CrudListener<Discount>() {
            @Override
            public Collection<Discount> findAll() {
                return paymentTerm != null ? discountRepository.findByPaymentTerm(paymentTerm) : new ArrayList<Discount>();
            }

            @Override
            public Discount add(Discount discount) {
                discount.setPaymentTerm(paymentTerm);
                return discountRepository.save(discount);
            }

            @Override
            public Discount update(Discount discount) {
                return discountRepository.save(discount);
            }

            @Override
            public void delete(Discount discount) {
                discountRepository.delete(discount);
            }
        });
        penaltyGrid.setCrudListener(new CrudListener<Penalty>() {
            @Override
            public Collection<Penalty> findAll() {
                return paymentTerm != null ? penaltyRepository.findByPaymentTerm(paymentTerm) : new ArrayList<Penalty>();
            }

            @Override
            public Penalty add(Penalty penalty) {
                penalty.setPaymentTerm(paymentTerm);
                return penaltyRepository.save(penalty);
            }

            @Override
            public Penalty update(Penalty penalty) {
                return penaltyRepository.save(penalty);
            }

            @Override
            public void delete(Penalty penalty) {
                penaltyRepository.delete(penalty);
            }
        });
        paymentTermGrid.getGrid().addSelectionListener(new SelectionListener<Grid<PaymentTerm>, PaymentTerm>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<PaymentTerm>, PaymentTerm> selectionEvent) {
                Optional<PaymentTerm> selection = selectionEvent.getFirstSelectedItem();
                if (selection.isPresent()) {
                    paymentTerm = selection.get();
                    detailsLayout.setEnabled(Boolean.TRUE);
                    splitLayout.setSplitterPosition(SPLITTER_DEFAULT_POSITION);
                } else {
                    paymentTerm = null;
                    detailsLayout.setEnabled(Boolean.FALSE);
                    splitLayout.setSplitterPosition(100);
                }
                penaltyGrid.refreshGrid();
                discountGrid.refreshGrid();
            }
        });
        detailsLayout.setEnabled(Boolean.FALSE);
    }
}
