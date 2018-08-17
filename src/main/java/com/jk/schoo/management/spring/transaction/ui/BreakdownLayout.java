package com.jk.schoo.management.spring.transaction.ui;

import com.jk.schoo.management.spring.transaction.enumconstant.AmountBreakdown;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Map;

public class BreakdownLayout extends HorizontalLayout {

    private Label outStandingLbl;
    private Label discountLbl;
    private Label penaltyLbl;
    private Label amountLbl;
    private Label paidLbl;

    public BreakdownLayout(){
        outStandingLbl = new Label();
        discountLbl = new Label();
        penaltyLbl = new Label();
        amountLbl = new Label();
        paidLbl = new Label();
        add(outStandingLbl);
        add(new Label(" = "));
        add(amountLbl);
        add(new Label(" - "));
        add(paidLbl);
        add(new Label(" - "));
        add(discountLbl);
        add(new Label(" + "));
        add(penaltyLbl);
        setWidth("100%");
        setVisible(Boolean.FALSE);
        setClassName("box");
    }

    public void update(Map<AmountBreakdown, Double> breakdown){
        String amountLblSuffix = "";
        Double outstanding = 0.0;
        if(breakdown != null) {
            Double discount = breakdown.get(AmountBreakdown.DISCOUNT);
            Double penalty = breakdown.get(AmountBreakdown.PENALTY);
            Double amount = breakdown.get(AmountBreakdown.AMOUNT);
            outstanding = breakdown.get(AmountBreakdown.OUTSTANDING);
            Double paid = breakdown.get(AmountBreakdown.PAID);
            outStandingLbl.setText(AmountBreakdown.OUTSTANDING.getName() + ": " + outstanding);
            discountLbl.setText(AmountBreakdown.DISCOUNT.getName() + ": " + discount);
            penaltyLbl.setText(AmountBreakdown.PENALTY.getName() + ": " + penalty);
            amountLbl.setText(AmountBreakdown.AMOUNT.getName() + ": " + amount);
            paidLbl.setText(AmountBreakdown.PAID.getName() + ": " + paid);
            outStandingLbl.setClassName(outstanding > 0 ? "error" : "success");
            discountLbl.setClassName(discount <= 0 ? "neutral" : "success");
            penaltyLbl.setClassName(penalty > 0 ? "error" : "neutral");
            amountLbl.setClassName("neutral");
            paidLbl.setClassName(paid <= 0 ? "neutral" : "success");
            setVisible(Boolean.TRUE);
        }else{
            setVisible(Boolean.FALSE);
        }
    }

}
