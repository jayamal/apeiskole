package com.jk.schoo.management.spring.transaction.domain;

import com.jk.schoo.management.spring.common.validation.core.Conditional;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.transaction.enumconstant.PaymentType;
import com.jk.schoo.management.spring.transaction.enumconstant.TransactionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by jayamalk on 7/9/2018.
 */
@Entity
@Conditional(selected = "paymentType", values = {"CHEQUE"}, required = {"paymentTypeReference"})
public class Transaction {

    public static final String FIELD_ID = "id";
    public static final String FIELD_PAYMENT_TYPE = "paymentType";
    public static final String FIELD_PAYMENT_TYPE_REFERENCE = "paymentTypeReference";
    public static final String FIELD_FEE = "fee";
    public static final String FIELD_AMOUNT = "amount";
    public static final String FIELD_STATUS = "status";
    public static final String FIELD_INITIATED_DATE_TIME = "initiatedDateTime";
    public static final String FIELD_LAST_UPDATED_DATE_TIME = "lastUpdatedDateTime";
    public static final String FIELD_INITIATOR = "initiator";
    public static final String FIELD_STUDENT = "student";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentType paymentType;
    private String paymentTypeReference;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fee_id")
    @NotNull
    private Fee fee;
    @NotNull
    private Double amount;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date initiatedDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDateTime;
    private String initiator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    @NotNull
    private Student student;
    private String invoice;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTypeReference() {
        return paymentTypeReference;
    }

    public void setPaymentTypeReference(String paymentTypeReference) {
        this.paymentTypeReference = paymentTypeReference;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Date getInitiatedDateTime() {
        return initiatedDateTime;
    }

    public void setInitiatedDateTime(Date initiatedDateTime) {
        this.initiatedDateTime = initiatedDateTime;
    }

    public Date getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Date lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }
}
