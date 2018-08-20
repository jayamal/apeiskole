package com.jk.schoo.management.spring;

import com.jk.schoo.management.spring.enrollment.ui.academicyear.AcademicYearView;
import com.jk.schoo.management.spring.report.ui.ReportView;
import com.jk.schoo.management.spring.student.ui.StudentCrudView;
import com.jk.schoo.management.spring.transaction.ui.FeeView;
import com.jk.schoo.management.spring.transaction.ui.TransactionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The main view contains a simple label element and a template element.
 */
@HtmlImport("styles/shared-styles.html")
@Route
@UIScope
public class MainView extends VerticalLayout {

    private Component currentView;

    public MainView(@Autowired StudentCrudView studentCrudView,
                    @Autowired TransactionView transactionView,
                    @Autowired ReportView reportView,
                    @Autowired AcademicYearView academicYearView,
                    @Autowired FeeView feeView) {
        //Actions
        Tab studentsTab = new Tab("Students");
        Tab transactionsTab = new Tab("Transactions");
        Tab reportsTab = new Tab("Reports");
        Tab academicYearTab = new Tab("Academic Years");
        Tab feeViewTab = new Tab("Fees");
        Tabs tabs = new Tabs(academicYearTab, feeViewTab, studentsTab, transactionsTab, reportsTab);
        add(tabs);
        tabs.setWidth("100%");
        tabs.addSelectedChangeListener(new ComponentEventListener<SelectedChangeEvent>() {
            @Override
            public void onComponentEvent(SelectedChangeEvent selectedChangeEvent) {
                if(currentView != null){
                    remove(currentView);
                }
                if(tabs.getSelectedTab().equals(studentsTab)){
                    currentView = studentCrudView;
                    studentCrudView.init();
                    add(currentView);
                }else if(tabs.getSelectedTab().equals(transactionsTab)){
                    currentView = transactionView;
                    add(currentView);
                    transactionView.init();
                }else if(tabs.getSelectedTab().equals(reportsTab)){
                    currentView = reportView;
                    add(currentView);
                    reportView.init();
                }else if(tabs.getSelectedTab().equals(academicYearTab)){
                    currentView = academicYearView;
                    add(academicYearView);
                    academicYearView.init();
                }else if(tabs.getSelectedTab().equals(feeViewTab)){
                    currentView = feeView;
                    add(feeView);
                    feeView.init();
                }
            }
        });
        tabs.setSelectedTab(studentsTab);
        currentView = studentCrudView;
        add(currentView);
        studentCrudView.init();
        setSizeFull();
        setPadding(Boolean.FALSE);
        setMargin(Boolean.FALSE);
        setSpacing(Boolean.FALSE);
    }

}
