package com.jk.schoo.management.spring.report.ui;

import com.jk.schoo.management.spring.report.domain.Report;
import com.jk.schoo.management.spring.report.domain.ReportStatus;
import com.jk.schoo.management.spring.report.domain.ReportType;
import com.jk.schoo.management.spring.report.domain.Reportable;
import com.jk.schoo.management.spring.report.service.ReportService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * Created by jayamalk on 7/9/2018.
 */
@SpringComponent
@UIScope
public class ReportDialog extends Dialog {

    private ReportService reportService;
    private ChangeHandler changeHandler;
    public interface ChangeHandler {
        void onChange();
    }

    @Autowired
    public ReportDialog(ReportService reportService){
        this.reportService = reportService;
    }

    public void init(){
        removeAll();
        ComboBox<Reportable> reportableComboBox = new ComboBox<>();
        reportableComboBox.setLabel("Report");
        reportableComboBox.setItemLabelGenerator(new ItemLabelGenerator<Reportable>() {
            @Override
            public String apply(Reportable reportable) {
                return reportable.getTitle();
            }
        });
        Button exportBtn = new Button("Export");
        Button cancelBtn = new Button("Cancel");
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(exportBtn, cancelBtn);

        actions.setWidth("100%");
        add(reportableComboBox, actions);

        cancelBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                ReportDialog.this.close();
            }
        });

        exportBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                Reportable reportable = reportableComboBox.getValue();
                //Control entry
                Report report = new Report();
                report.setTitle(reportable.getTitle());
                report.setInitiator("ADMIN");
                report.setType(ReportType.MANUAL);
                report.setInitiatedDateTime(Calendar.getInstance().getTime());
                report.setStatus(ReportStatus.PENDING);
                reportService.update(report);
                //Generate Report
                report.setPath(reportable.generate(null));
                report.setGeneratedDateTime(Calendar.getInstance().getTime());
                report.setStatus(ReportStatus.GENERATED);
                reportService.update(report);
                changeHandler.onChange();
                ReportDialog.this.close();
            }
        });
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
