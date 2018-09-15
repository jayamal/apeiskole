package com.jk.schoo.management.spring.customer.view;

import com.jk.schoo.management.spring.MainView;
import com.jk.schoo.management.spring.customer.domain.Customer;
import com.jk.schoo.management.spring.customer.domain.CustomerDiscount;
import com.jk.schoo.management.spring.customer.service.CustomerRepository;
import com.jk.schoo.management.spring.invoice.domain.Discount;
import com.jk.schoo.management.spring.invoice.service.DiscountRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.FieldProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jayamalk on 9/5/2018.
 */
@Route(value = "customer", layout = MainView.class)
@UIScope
public class CustomerView extends HorizontalLayout{

    private Customer customer;
    private static final Double SPLITTER_DEFAULT_POSITION = 80.0;

    @Autowired
    public CustomerView(CustomerRepository customerRepository, DiscountRepository discountRepository) {
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        GridCrud<Customer> crudCustomerGrid = new GridCrud<>(Customer.class);
        GridCrud<CustomerDiscount> crudCustomerDiscountGrid = new GridCrud<>(CustomerDiscount.class);
        crudCustomerDiscountGrid.getCrudFormFactory().setVisibleProperties(CustomerDiscount.DISCOUNT);
        crudCustomerDiscountGrid.getCrudFormFactory().setUseBeanValidation(true);
        crudCustomerDiscountGrid.setUpdateOperationVisible(Boolean.FALSE);
        crudCustomerDiscountGrid.setSizeFull();
        crudCustomerGrid.getCrudFormFactory().setVisibleProperties(Customer.NAME);
        crudCustomerGrid.getGrid().setColumns(Customer.ID, Customer.NAME);
        crudCustomerGrid.getCrudFormFactory().setUseBeanValidation(true);
        crudCustomerGrid.setSizeFull();
        setSizeFull();
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(crudCustomerGrid);
        splitLayout.addToSecondary(crudCustomerDiscountGrid);
        splitLayout.setSplitterPosition(100);
        add(splitLayout);
        crudCustomerGrid.setCrudListener(new CrudListener<Customer>() {
            @Override
            public Collection<Customer> findAll() {
                return customerRepository.findAll();
            }

            @Override
            public Customer add(Customer customer) {
                return customerRepository.save(customer);
            }

            @Override
            public Customer update(Customer customer) {
                return customerRepository.save(customer);
            }

            @Override
            public void delete(Customer customer) {
                customerRepository.delete(customer);
            }
        });
        crudCustomerDiscountGrid.setCrudListener(new CrudListener<CustomerDiscount>() {
            @Override
            public Collection<CustomerDiscount> findAll() {
                if(customer != null){
                    customer = customerRepository.getOne(customer.getId());
                }
                return customer != null ? customer.getDiscounts().stream().map(discount -> new CustomerDiscount(discount)).collect(Collectors.toList()) : new ArrayList<CustomerDiscount>();
            }

            @Override
            public CustomerDiscount add(CustomerDiscount customerDiscount) {
                customerDiscount.getDiscount().getCustomers().add(customer);
                customerDiscount.setDiscount(discountRepository.save(customerDiscount.getDiscount()));
                return customerDiscount;
            }

            @Override
            public CustomerDiscount update(CustomerDiscount customerDiscount) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void delete(CustomerDiscount customerDiscount) {
                customerDiscount.getDiscount().getCustomers().remove(customer);
                customerDiscount.setDiscount(discountRepository.save(customerDiscount.getDiscount()));
            }
        });
        crudCustomerDiscountGrid.getCrudFormFactory().setFieldProvider(CustomerDiscount.DISCOUNT, new FieldProvider<ComboBox<Discount>, Discount>() {
            @Override
            public HasValueAndElement<AbstractField.ComponentValueChangeEvent<ComboBox<Discount>, Discount>, Discount> buildField() {
                List<Discount> result = discountRepository.findAll();
                return new ComboBox<Discount>("", result != null ? result : new ArrayList<Discount>());
            }
        });
        crudCustomerGrid.getGrid().addSelectionListener(new SelectionListener<Grid<Customer>, Customer>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<Customer>, Customer> selectionEvent) {
                if(selectionEvent.getFirstSelectedItem().isPresent()){
                    customer = selectionEvent.getFirstSelectedItem().get();
                    crudCustomerDiscountGrid.refreshGrid();
                    splitLayout.setSplitterPosition(SPLITTER_DEFAULT_POSITION);
                }else{
                    customer = null;
                    splitLayout.setSplitterPosition(100);
                }
            }
        });
    }
}