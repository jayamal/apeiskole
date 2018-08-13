package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.jk.schoo.management.spring.transaction.enumconstant.AmountBreakdown;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.dom.Element;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;

public class TransactionCrudFormFactory extends DefaultCrudFormFactory<Transaction> {

    private Map<String, HasValue> fields;
    private FormLayout.ResponsiveStep[] responsiveSteps;
    private BreakdownLayout breakdownLayout;

    public TransactionCrudFormFactory(Class<Transaction> domainType) {
        super(domainType);
        fields = new HashMap<>();
        this.responsiveSteps = new FormLayout.ResponsiveStep[]{new FormLayout.ResponsiveStep("0em", 1), new FormLayout.ResponsiveStep("25em", 2)};

    }

    @Override
    protected void bindField(HasValue field, String property, Class<?> propertyType) {
        super.bindField(field, property, propertyType);
        if(fields == null){
            fields = new HashMap<>();
        }
        fields.put(property, field);
    }


    @Override
    public Component buildNewForm(CrudOperation operation, Transaction domainObject, boolean readOnly, ComponentEventListener<ClickEvent<Button>> cancelButtonClickListener, ComponentEventListener<ClickEvent<Button>> operationButtonClickListener) {
        breakdownLayout = new BreakdownLayout();
        breakdownLayout.setWidth("100%");
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        formLayout.setResponsiveSteps(this.responsiveSteps);
        List<HasValueAndElement> fields = this.buildFields(operation, domainObject, readOnly);
        fields.stream().forEach((field) -> {
            Element var10000 = (Element)formLayout.getElement().appendChild(new Element[]{field.getElement()});
        });
        Component footerLayout = this.buildFooter(operation, domainObject, cancelButtonClickListener, operationButtonClickListener);
        VerticalLayout mainLayout = new VerticalLayout(new Component[]{breakdownLayout, formLayout, footerLayout});
        mainLayout.setFlexGrow(1.0D, new HasElement[]{formLayout});
        mainLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.START, new Component[]{breakdownLayout});
        mainLayout.setHorizontalComponentAlignment(FlexComponent.Alignment.END, new Component[]{footerLayout});
        mainLayout.setMargin(false);
        mainLayout.setPadding(false);
        mainLayout.setSpacing(true);
        this.binder.withValidator(new Validator<Transaction>() {
            @Override
            public ValidationResult apply(Transaction transaction, ValueContext valueContext) {
                System.out.println(transaction.getPaymentType());
                ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                javax.validation.Validator validator = validatorFactory.getValidator();
                Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
                Optional<ConstraintViolation<Transaction>> result = violations.stream().filter(consViolation -> configurations.get(operation).getVisibleProperties().contains(consViolation.getPropertyPath().toString())).findFirst();
                if(result.isPresent()){
                    ((TextField)getGeneratedField(result.get().getPropertyPath().toString())).setErrorMessage("Payment Reference is Required for Payment Type " + transaction.getPaymentType().getName());
                    return ValidationResult.error(result.get().getMessage());
                }else{
                    return ValidationResult.ok();
                }
            }
        });
        return mainLayout;
    }

    public void updateBreakdown(Map<AmountBreakdown, Double> breakdown){
        breakdownLayout.update(breakdown);
    }

    public HasValue getGeneratedField(String property){
        return fields.get(property);
    }
}
