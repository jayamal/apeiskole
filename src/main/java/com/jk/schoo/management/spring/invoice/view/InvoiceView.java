package com.jk.schoo.management.spring.invoice.view;

import com.jk.schoo.management.spring.MainView;
import com.jk.schoo.management.spring.customer.domain.Customer;
import com.jk.schoo.management.spring.customer.service.CustomerRepository;
import com.jk.schoo.management.spring.invoice.domain.*;
import com.jk.schoo.management.spring.invoice.report.PaymentReceiptReport;
import com.jk.schoo.management.spring.invoice.service.PaymentTermRepository;
import com.jk.schoo.management.spring.invoice.service.TransactionRepository;
import com.jk.schoo.management.spring.invoice.service.TransactionService;
import com.jk.schoo.management.spring.offering.domain.Offering;
import com.jk.schoo.management.spring.offering.service.OfferingRepository;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.crudui.crud.CrudListener;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.CrudFormFactory;
import org.vaadin.crudui.form.impl.field.provider.AbstractListingProvider;

import java.util.*;

/**
 * Created by jayamalk on 9/5/2018.
 */
@Route(value = "invoice", layout = MainView.class)
@UIScope
public class InvoiceView extends VerticalLayout {

    private TransactionRepository transactionRepository;
    private OfferingRepository offeringRepository;
    private PaymentTermRepository paymentTermRepository;
    private CustomerRepository customerRepository;
    private ComboBox<Customer> customerComboBox;
    private ComboBox<Offering> offeringComboBox;
    private ComboBox<PaymentTerm> paymentTermComboBox;
    private Transaction currentTransaction;
    private static final Double SPLITTER_DEFAULT_POSITION = 60.0;

    @Autowired
    public InvoiceView(CustomerRepository customerRepository, TransactionRepository invoiceRepository, OfferingRepository offeringRepository, PaymentTermRepository paymentTermRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
        currentTransaction = null;
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
        this.customerRepository = customerRepository;
        this.transactionRepository = invoiceRepository;
        this.offeringRepository = offeringRepository;
        this.paymentTermRepository = paymentTermRepository;
        GridCrud<Transaction> crudInvoiceGrid = new GridCrud<>(Transaction.class);
        GridCrud<Transaction> crudTransactionGrid = new GridCrud<>(Transaction.class);
        crudTransactionGrid.setDeleteOperationVisible(Boolean.FALSE);
        crudInvoiceGrid.getCrudFormFactory().setVisibleProperties(
                Transaction.CUSTOMER,
                Transaction.OFFERING,
                Transaction.PAYMENT_TERM,
                Transaction.TRANSACTION_DATE,
                Transaction.DUE_DATE
        );
        crudInvoiceGrid.getGrid().setColumns(
                Transaction.ID,
                Transaction.TRANSACTION_STATUS,
                Transaction.TRANSACTION_TYPE,
                Transaction.CUSTOMER,
                Transaction.OFFERING,
                Transaction.PAYMENT_TERM,
                Transaction.TRANSACTION_DATE,
                Transaction.DUE_DATE,
                Transaction.REMARK
        );
        crudTransactionGrid.getCrudFormFactory().setVisibleProperties(
                Transaction.AMOUNT,
                Transaction.PAYMENT_TYPE,
                Transaction.PAYMENT_TYPE_REFERENCE
        );
        crudTransactionGrid.getGrid().setColumns(
                Transaction.ID,
                Transaction.TRANSACTION_STATUS,
                Transaction.TRANSACTION_TYPE,
                Transaction.TRANSACTION_DATE,
                Transaction.AMOUNT,
                Transaction.PAYMENT_TYPE,
                Transaction.PAYMENT_TYPE_REFERENCE,
                Transaction.REMARK
        );
        crudInvoiceGrid.setDeleteOperationVisible(Boolean.FALSE);
        crudInvoiceGrid.getCrudFormFactory().setUseBeanValidation(true);
        crudTransactionGrid.getCrudFormFactory().setUseBeanValidation(true);
        initForm(crudInvoiceGrid.getCrudFormFactory());
        setSizeFull();
        crudInvoiceGrid.setSizeFull();
        crudTransactionGrid.setSizeFull();
        HorizontalLayout detailsLayout = new HorizontalLayout();
        detailsLayout.add(crudTransactionGrid);
        detailsLayout.setSizeFull();
        detailsLayout.setEnabled(Boolean.FALSE);
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitLayout.setSizeFull();
        splitLayout.addToPrimary(crudInvoiceGrid);
        splitLayout.addToSecondary(detailsLayout);
        splitLayout.setSplitterPosition(100);
        add(splitLayout);
        crudInvoiceGrid.setCrudListener(new CrudListener<Transaction>() {
            @Override
            public Collection<Transaction> findAll() {
                return invoiceRepository.findByTransactionType(TransactionType.INVOICE);
            }

            @Override
            public Transaction add(Transaction transaction) {
                transaction.setTransactionType(TransactionType.INVOICE);
                transaction.setTransactionStatus(TransactionStatus.OPEN);
                return transactionService.createTransaction(transaction);
            }

            @Override
            public Transaction update(Transaction transaction) {
                return invoiceRepository.save(transaction);
            }

            @Override
            public void delete(Transaction transaction) {
                throw new UnsupportedOperationException();
            }
        });
        crudInvoiceGrid.getGrid().addSelectionListener(new SelectionListener<Grid<Transaction>, Transaction>() {
            @Override
            public void selectionChange(SelectionEvent<Grid<Transaction>, Transaction> selectionEvent) {
                Optional<Transaction> selected = selectionEvent.getFirstSelectedItem();
                if (selected.isPresent()) {
                    currentTransaction = selected.get();
                    crudTransactionGrid.refreshGrid();
                    detailsLayout.setEnabled(Boolean.TRUE);
                    splitLayout.setSplitterPosition(SPLITTER_DEFAULT_POSITION);
                } else {
                    currentTransaction = null;
                    detailsLayout.setEnabled(Boolean.FALSE);
                    splitLayout.setSplitterPosition(100);
                }
            }
        });
        crudTransactionGrid.setCrudListener(new CrudListener<Transaction>() {
            @Override
            public Collection<Transaction> findAll() {
                List<Transaction> transactions = new ArrayList<Transaction>();
                if (currentTransaction != null) {
                    try {
                        currentTransaction = invoiceRepository.getOne(currentTransaction.getId());
                        List<Transaction> penaltyTransactions = transactionService.generateInMemoryPenaltyTransaction(currentTransaction);
                        transactions.addAll(penaltyTransactions);
                        transactions.addAll(currentTransaction.getTransactionsIncludingSelf());
                        TransactionSummary transactionSummary = transactionService.getTransactionSummary(currentTransaction, transactions);
                        crudTransactionGrid.getGrid().getColumnByKey(Transaction.AMOUNT).setFooter("Balance: " + transactionSummary.getOutstanding());
                        crudTransactionGrid.getGrid().getColumnByKey(Transaction.TRANSACTION_DATE).setFooter("Amount: " + transactionSummary.getTotal());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                return transactions;
            }

            @Override
            public Transaction add(Transaction transaction) {
                transaction.setTransaction(currentTransaction);
                transaction.setTransactionType(TransactionType.PAYMENT);
                transaction.setTransactionStatus(TransactionStatus.CLOSED);
                Date date = new Date();
                transaction.setDueDate(date);
                transaction.setTransactionDate(date);
                Transaction saved = null;
                try {
                    saved = transactionService.createPaymentTransaction(transaction);
                } catch (Exception e) {
                    e.printStackTrace();
                    Notification.show(e.getMessage());
                }
                return saved;
            }

            @Override
            public Transaction update(Transaction transactionBack) {
                transactionBack.setId(null);
                return transactionRepository.save(transactionBack);
            }

            @Override
            public void delete(Transaction transactionBack) {
                throw new UnsupportedOperationException();
            }
        });
        crudTransactionGrid.getGrid().addColumn(new ComponentRenderer<>(transaction -> {
            HorizontalLayout buttons = new HorizontalLayout();
            if(TransactionStatus.CLOSED.equals(transaction.getTransactionStatus()) && TransactionType.PAYMENT.equals(transaction.getTransactionType())) {
                Anchor downloadLink = new Anchor(PaymentReceiptReport.generate(transaction), "Print");
                downloadLink.setClassName("button");
                buttons.add(downloadLink);
            }
            return buttons;
        })).setHeader("Actions");
        crudInvoiceGrid.getGrid().setItemDetailsRenderer(new ComponentRenderer<>(invoice -> {
            VerticalLayout layout = new VerticalLayout();
            for(Transaction transaction : invoice.getTransactions()){
                if(TransactionType.PAYMENT.equals(transaction.getTransactionType())){
                    HorizontalLayout paymentContainer = new HorizontalLayout();
                    paymentContainer.setWidth("100%");
                    paymentContainer.add(new Label("Payment: RS " + transaction.getAmount() + " received on " + transaction.getTransactionDate().toString() + ".  "));
                    StreamResource streamSource = PaymentReceiptReport.generate(transaction);
                    Anchor downloadLink = new Anchor(streamSource, "Print");
                    paymentContainer.add(downloadLink);
                    layout.add(paymentContainer);
                }
            }
            layout.setWidth("100%");
            return layout;
        }));
    }

    private void initForm(CrudFormFactory<Transaction> crudFormFactory) {
        crudFormFactory.setFieldProvider(Transaction.CUSTOMER, new AbstractListingProvider<ComboBox<Customer>, Customer>(customerRepository.findAll()) {
            @Override
            protected ComboBox<Customer> buildAbstractListing() {
                customerComboBox = new ComboBox<Customer>();
                customerComboBox.setItems(items != null ? items : new ArrayList<Customer>());
                customerComboBox.setItemLabelGenerator(new ItemLabelGenerator<Customer>() {
                    @Override
                    public String apply(Customer customer) {
                        return customer.getName();
                    }
                });
                return customerComboBox;
            }
        });
        crudFormFactory.setFieldProvider(Transaction.OFFERING, new AbstractListingProvider<ComboBox<Offering>, Offering>(offeringRepository.findAll()) {
            @Override
            protected ComboBox<Offering> buildAbstractListing() {
                offeringComboBox = new ComboBox<Offering>();
                offeringComboBox.setItems(items != null ? items : new ArrayList<Offering>());
                offeringComboBox.setItemLabelGenerator(new ItemLabelGenerator<Offering>() {
                    @Override
                    public String apply(Offering offering) {
                        return offering.getSalesDescription();
                    }
                });
                return offeringComboBox;
            }
        });
        crudFormFactory.setFieldProvider(Transaction.PAYMENT_TERM, new AbstractListingProvider<ComboBox<PaymentTerm>, PaymentTerm>(paymentTermRepository.findAll()) {
            @Override
            protected ComboBox<PaymentTerm> buildAbstractListing() {
                paymentTermComboBox = new ComboBox<PaymentTerm>();
                paymentTermComboBox.setItems(items != null ? items : new ArrayList<PaymentTerm>());
                paymentTermComboBox.setItemLabelGenerator(new ItemLabelGenerator<PaymentTerm>() {
                    @Override
                    public String apply(PaymentTerm paymentTerm) {
                        return paymentTerm.getName();
                    }
                });
                return paymentTermComboBox;
            }
        });
    }
}