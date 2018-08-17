package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.report.util.ReportUtil;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.dao.StudentRepository;
import com.jk.schoo.management.spring.transaction.domain.Fee;
import com.jk.schoo.management.spring.transaction.domain.Reference;
import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.jk.schoo.management.spring.transaction.enumconstant.AmountBreakdown;
import com.jk.schoo.management.spring.transaction.enumconstant.PaymentType;
import com.jk.schoo.management.spring.transaction.enumconstant.TransactionStatus;
import com.jk.schoo.management.spring.transaction.report.StudentFeeInvoice;
import com.jk.schoo.management.spring.transaction.service.dao.FeeRepository;
import com.jk.schoo.management.spring.transaction.service.dao.TransactionRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.FieldCreationListener;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jayamalk on 7/5/2018.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransactionView extends VerticalLayout {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FeeRepository feeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentFeeInvoice studentFeeInvoice;
    private TransactionCrudFormFactory transactionTransactionCrudFormFactory;

    public void init(){
        removeAll();
        GridCrud<Transaction> transactionGridCrud = new GridCrud<>(Transaction.class);
        transactionTransactionCrudFormFactory = new TransactionCrudFormFactory(Transaction.class, studentRepository);
        transactionTransactionCrudFormFactory.setUseBeanValidation(Boolean.TRUE);
        transactionGridCrud.setUpdateOperationVisible(Boolean.FALSE);
        transactionGridCrud.setDeleteOperationVisible(Boolean.FALSE);
        transactionGridCrud.setCrudFormFactory(transactionTransactionCrudFormFactory);
        transactionGridCrud.getCrudFormFactory().setVisibleProperties(Transaction.FIELD_FEE, Transaction.FIELD_STUDENT, Transaction.FIELD_AMOUNT, Transaction.FIELD_PAYMENT_TYPE, Transaction.FIELD_PAYMENT_TYPE_REFERENCE);
        ComboBox<Fee> feeComboBox = new ComboBox<>();
        ComboBox<Reference> referenceComboBox = new ComboBox<>();
        ComboBox<PaymentType> paymentTypeComboBox = new ComboBox<>();
        final TextField[] paymentTypeRefTextBox = new TextField[1];
        final TextField[] amountRefTextBox = new TextField[1];
        transactionGridCrud.getCrudFormFactory().setFieldCreationListener(Transaction.FIELD_PAYMENT_TYPE_REFERENCE, new FieldCreationListener() {
            @Override
            public void fieldCreated(HasValue hasValue) {
                paymentTypeRefTextBox[0] = (TextField) hasValue;
            }
        });
        transactionGridCrud.getCrudFormFactory().setFieldCreationListener(Transaction.FIELD_AMOUNT, new FieldCreationListener() {
            @Override
            public void fieldCreated(HasValue hasValue) {
                amountRefTextBox[0] = (TextField) hasValue;
            }
        });
        transactionGridCrud.getCrudFormFactory().setFieldProvider(Transaction.FIELD_FEE, new AbstractListingProvider<ComboBox<Fee>, Fee>(feeRepository.findAll()) {
            @Override
            protected ComboBox<Fee> buildAbstractListing() {
                feeComboBox.setItems(items != null ? items : new ArrayList<Fee>());
                feeComboBox.setItemLabelGenerator(new ItemLabelGenerator<Fee>() {
                    @Override
                    public String apply(Fee fee) {
                        return fee.getReferenceIdDisplayName();
                    }
                });
                feeComboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Fee>, Fee>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<Fee>, Fee> comboBoxFeeComponentValueChangeEvent) {
                        List<? extends Reference> references =   comboBoxFeeComponentValueChangeEvent.getValue() != null ? comboBoxFeeComponentValueChangeEvent.getValue().getSemester().getBatches().stream().flatMap(batches -> batches.getStudents().stream()).collect(Collectors.toList()) : new ArrayList<Reference>();
                        referenceComboBox.setItems((Collection<Reference>) references);
                    }
                });
                return feeComboBox;
            }
        });
        transactionGridCrud.getCrudFormFactory().setFieldProvider(Transaction.FIELD_STUDENT, new AbstractListingProvider<ComboBox<Reference>, Reference>(new ArrayList<Reference>()) {
            @Override
            protected ComboBox<Reference> buildAbstractListing() {
                referenceComboBox.setItems(new ArrayList<>());
                referenceComboBox.setItemLabelGenerator(new ItemLabelGenerator<Reference>() {
                    @Override
                    public String apply(Reference reference) {
                        return reference.getReferenceIdDisplayName();
                    }
                });
                referenceComboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<Reference>, Reference>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<Reference>, Reference> comboBoxReferenceComponentValueChangeEvent) {
                        Student student = (Student) comboBoxReferenceComponentValueChangeEvent.getValue();
                        if(student != null) {
                            Optional<Student> refreshedStudent = studentRepository.findById(student.getId());
                            if(refreshedStudent.isPresent()){
                                Map<AmountBreakdown, Double> breakdown = refreshedStudent.get().getAmountBreakdown(feeComboBox.getValue());
                                Double outstanding = breakdown.get(AmountBreakdown.OUTSTANDING);
                                amountRefTextBox[0].setValue(String.valueOf(outstanding));
                                transactionTransactionCrudFormFactory.updateBreakdown(breakdown);
                            }else{
                                amountRefTextBox[0].setValue("");
                                transactionTransactionCrudFormFactory.updateBreakdown(null);
                            }
                        }else{
                            amountRefTextBox[0].setValue("");
                            transactionTransactionCrudFormFactory.updateBreakdown(null);
                        }
                    }
                });
                return referenceComboBox;
            }
        });
        transactionGridCrud.getCrudFormFactory().setFieldProvider(Transaction.FIELD_PAYMENT_TYPE, new AbstractListingProvider<ComboBox<PaymentType>, PaymentType>(Arrays.asList(PaymentType.values())) {
            @Override
            protected ComboBox<PaymentType> buildAbstractListing() {
                paymentTypeComboBox.setItems(items);
                paymentTypeComboBox.setItemLabelGenerator(new ItemLabelGenerator<PaymentType>() {
                    @Override
                    public String apply(PaymentType paymentType) {
                        return paymentType.getName();
                    }
                });
                paymentTypeComboBox.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<PaymentType>, PaymentType>>() {
                    @Override
                    public void valueChanged(AbstractField.ComponentValueChangeEvent<ComboBox<PaymentType>, PaymentType> comboBoxPaymentTypeComponentValueChangeEvent) {
                        if(comboBoxPaymentTypeComponentValueChangeEvent.getValue() != null && comboBoxPaymentTypeComponentValueChangeEvent.getValue().equals(PaymentType.CHEQUE)){
                            paymentTypeRefTextBox[0].setRequired(Boolean.TRUE);
                            paymentTypeRefTextBox[0].setEnabled(Boolean.TRUE);
                        }else{
                            paymentTypeRefTextBox[0].clear();
                            paymentTypeRefTextBox[0].setRequired(Boolean.FALSE);
                            paymentTypeRefTextBox[0].setEnabled(Boolean.FALSE);
                        }
                    }
                });
                return paymentTypeComboBox;
            }
        });
        transactionGridCrud.getGrid().setColumns();
        //Add columns
        transactionGridCrud.getGrid().addColumn(new ComponentRenderer<>(transaction -> {
            HorizontalLayout buttons = new HorizontalLayout();
                Anchor downloadLink = new Anchor(studentFeeInvoice.generate(transaction), "Download");
                downloadLink.getElement().setAttribute("download", true);
                buttons.add(downloadLink);
            return buttons;
        })).setHeader("Actions");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getId()).setHeader("ID");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getFee().getReferenceIdDisplayName()).setHeader("Fee");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getStudent() == null ? "" : transaction.getStudent().getReferenceIdDisplayName()).setHeader("Student Reference");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getAmount()).setHeader("Amount");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getPaymentType().getName()).setHeader("Payment Type");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getPaymentTypeReference() != null ? transaction.getPaymentTypeReference() : "").setHeader("Payment Type Reference");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getStatus() != null ? transaction.getStatus().getName() : "").setHeader("Status");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getInitiatedDateTime()).setHeader("Initiated Date Time");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getInitiator()).setHeader("Initiator");
        transactionGridCrud.getGrid().addColumn(transaction->transaction.getLastUpdatedDateTime() != null ? transaction.getLastUpdatedDateTime() : "").setHeader("Last Updated Date Time");

        transactionGridCrud.setCrudListener(new CrudListener<Transaction>() {
            @Override
            public Collection<Transaction> findAll() {
                return transactionRepository.findAll();
            }

            @Override
            public Transaction add(Transaction transaction) {
                transaction.setInitiatedDateTime(Calendar.getInstance().getTime());
                enrich(transaction);
                return transactionRepository.save(transaction);
            }

            @Override
            public Transaction update(Transaction transaction) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void delete(Transaction transaction) {
                throw new UnsupportedOperationException();
            }
        });

        add(transactionGridCrud);
        setSizeFull();
        transactionGridCrud.setSizeFull();

    }

    private void enrich(Transaction transaction){
        switch (transaction.getPaymentType()){
            case CASH:
                transaction.setStatus(TransactionStatus.ACCEPTED);
                break;
            case CHEQUE:
                transaction.setStatus(TransactionStatus.ACCEPTED);
                break;
            case REFUND:
                transaction.setStatus(TransactionStatus.REFUNDED);
                transaction.setAmount(transaction.getAmount() > 0 ? -transaction.getAmount() : transaction.getAmount());
                break;
                default:
                    transaction.setStatus(null);
        }
        transaction.setInitiator("WEB_ADMIN");
    }

}
