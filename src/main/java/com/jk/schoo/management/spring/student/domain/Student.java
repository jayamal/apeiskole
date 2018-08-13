package com.jk.schoo.management.spring.student.domain;

import com.jk.schoo.management.spring.enrollment.domain.Batch;
import com.jk.schoo.management.spring.transaction.domain.Fee;
import com.jk.schoo.management.spring.transaction.domain.Reference;
import com.jk.schoo.management.spring.transaction.domain.Transaction;
import com.jk.schoo.management.spring.transaction.enumconstant.AmountBreakdown;
import com.jk.schoo.management.spring.transaction.enumconstant.TransactionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by jayamalk on 7/5/2018.
 */
@Entity
public class Student implements Reference{

    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_BRANCH = "branch";
    public static final String FIELD_CREATE_DATE_TIME = "createDateTime";
    public static final String FIELD_LAST_UPDATED_DATE_TIME = "lastUpdatedDateTime";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDateTime;
    private String branch;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "students")
    private Set<Batch> batches = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true)
    private Set<Transaction> transactions = new HashSet<>();

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Batch> getBatches() {
        return batches;
    }

    public void setBatches(Set<Batch> batches) {
        this.batches = batches;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Date getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Date lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    @Override
    public String getReferenceId() {
        return String.valueOf(this.id);
    }

    @Override
    public String getReferenceIdDisplayName() {
        return "[ " + this.id + " ] " + this.name;
    }

    @Override
    public String getReferenceType() {
        return "student";
    }

    @Override
    public String getReferenceTypeDisplayName() {
        return "Student";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return getId() != null ? getId().equals(student.getId()) : student.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public Map<AmountBreakdown, Double> getAmountBreakdown(Fee fee){
        Set<Transaction> transactions = this.getTransactions();
        Double discount = 0.0;
        Double penalty = 0.0;
        Double amount = fee != null ? fee.getAmount() : 0.0;
        Double outstanding = 0.0;
        Double paid = 0.0;
        if(transactions != null) {
            for (Transaction transaction : transactions) {
                if(fee.getId().equals(transaction.getFee().getId())) {
                    paid += transaction.getAmount();
                }
            }
        }
        outstanding = amount - paid - discount + penalty;
        Map<AmountBreakdown, Double> amountBreakdownDoubleMap = new HashMap<>();
        amountBreakdownDoubleMap.put(AmountBreakdown.OUTSTANDING, outstanding);
        amountBreakdownDoubleMap.put(AmountBreakdown.AMOUNT, amount);
        amountBreakdownDoubleMap.put(AmountBreakdown.PAID, paid);
        amountBreakdownDoubleMap.put(AmountBreakdown.DISCOUNT, discount);
        amountBreakdownDoubleMap.put(AmountBreakdown.PENALTY, penalty);
        return amountBreakdownDoubleMap;
    }
}
