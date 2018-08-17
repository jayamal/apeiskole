package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.Properties;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.dao.StudentRepository;
import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.jk.schoo.management.spring.transaction.enumconstant.AmountBreakdown;
import com.jk.schoo.management.spring.transaction.enumconstant.PaymentType;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
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
    private StudentRepository studentRepository;

    public TransactionCrudFormFactory(Class<Transaction> domainType, StudentRepository studentRepository) {
        super(domainType);
        this.studentRepository = studentRepository;
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
                ValidationResult validationResult = ValidationResult.ok();
                ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                javax.validation.Validator validator = validatorFactory.getValidator();
                Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);
                Optional<ConstraintViolation<Transaction>> result = violations.stream().filter(consViolation -> configurations.get(operation).getVisibleProperties().contains(consViolation.getPropertyPath().toString())).findFirst();
                if(result.isPresent()){
                    validationResult = ValidationResult.error(result.get().getMessage());
                }else{
                    //Total Amount Validation
                    Optional<Student> refreshedStudent = studentRepository.findById(transaction.getStudent().getId());
                    if(refreshedStudent.isPresent()) {
                        Map<AmountBreakdown, Double> amountBreakdown = refreshedStudent.get().getAmountBreakdown(transaction.getFee());
                        if (transaction.getPaymentType().equals(PaymentType.REFUND)) {
                            //Amount should not exceed more than the paid amount
                            if(amountBreakdown.get(AmountBreakdown.PAID) < transaction.getAmount()){
                                validationResult = ValidationResult.error("Amount: " + transaction.getAmount() + " is greater than paid amount: " + amountBreakdown.get(AmountBreakdown.PAID));
                            }
                        } else {
                            //Amount should not exceed more than the outstanding amount
                            if(amountBreakdown.get(AmountBreakdown.OUTSTANDING) < transaction.getAmount()){
                                validationResult = ValidationResult.error("Amount: " + transaction.getAmount() + " is greater than outstanding amount: " + amountBreakdown.get(AmountBreakdown.OUTSTANDING));
                            }
                        }
                    }
                    if(!validationResult.isError() && transaction.getAmount() <= 0.0){
                        validationResult = ValidationResult.error("Amount: " + transaction.getAmount() + " should be greater than 0.0");
                    }
                }
                if(validationResult.isError()) {
                    new Notification(validationResult.getErrorMessage(), Properties.NOTIFICATION_DELAY).setOpened(Boolean.TRUE);
                }
                return validationResult;
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
