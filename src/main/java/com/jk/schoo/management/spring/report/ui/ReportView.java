package com.jk.schoo.management.spring.report.ui;

import com.jk.schoo.management.spring.ApplicationProperties;
import com.jk.schoo.management.spring.report.domain.Report;
import com.jk.schoo.management.spring.report.domain.ReportStatus;
import com.jk.schoo.management.spring.report.service.ReportService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jayamalk on 7/5/2018.
 */
@SpringComponent
@UIScope
public class ReportView extends VerticalLayout{

    private Grid<Report> grid;
    private ReportService reportService;
    @Autowired
    private ReportDialog reportDialog;

    @Autowired
    public ReportView(ReportService reportService) {
        this.reportService = reportService;
    }

    public void init(){
        removeAll();
        //Title
        Label label = new Label("Reports");
        add(label);
        //Actions
        Button newBtn = new Button("New");
        Button refreshBtn = new Button("Refresh");
        HorizontalLayout actionContainer = new HorizontalLayout();
        actionContainer.setWidth("100%");
        actionContainer.add(newBtn, refreshBtn);
        add(actionContainer);
        //Grid
        grid = new Grid<>();
        //Value providers
        List<ValueProvider<Report, String>> valueProviders = new ArrayList<>();
        valueProviders.add(report -> String.valueOf(report.getId()));
        valueProviders.add(Report::getTitle);
        valueProviders.add(report -> String.valueOf(report.getStatus() != null ? report.getStatus().getDisplayName() : null));
        valueProviders.add(report -> String.valueOf(report.getType() != null ? report.getType().getDisplayName() : null));
        valueProviders.add(report -> String.valueOf(report.getInitiatedDateTime()));
        valueProviders.add(report -> String.valueOf(report.getGeneratedDateTime()));
        valueProviders.add(Report::getInitiator);
        Iterator<ValueProvider<Report, String>> iterator = valueProviders.iterator();
        //Add columns
        grid.addColumn(new ComponentRenderer<>(report -> {
            HorizontalLayout buttons = new HorizontalLayout();
            if(report.getStatus().equals(ReportStatus.GENERATED)) {
                StreamResource streamSource = new StreamResource(report.getPath(), new InputStreamFactory() {
                    @Override
                    public InputStream createInputStream() {
                        File initialFile = new File(ApplicationProperties.EXPORT_PATH + File.separator + report.getPath());
                        InputStream targetStream = null;
                        try {
                            targetStream = new FileInputStream(initialFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return targetStream;
                    }

                    @Override
                    public boolean requiresLock() {
                        return false;
                    }
                });
                Anchor downloadLink = new Anchor(streamSource, "Download");
                downloadLink.getElement().setAttribute("download", true);
                buttons.add(downloadLink);
            }
            return buttons;
        })).setHeader("Actions");
        grid.addColumn(iterator.next()).setHeader("ID");
        grid.addColumn(iterator.next()).setHeader("Title").setFlexGrow(5);
        grid.addColumn(iterator.next()).setHeader("Status");
        grid.addColumn(iterator.next()).setHeader("Type");
        grid.addColumn(iterator.next()).setHeader("Initiated On");
        grid.addColumn(iterator.next()).setHeader("Generated On");
        grid.addColumn(iterator.next()).setHeader("Initiator");
        //Add filters
/*        HeaderRow filterRow = grid.appendHeaderRow();
        Iterator<ValueProvider<Report, String>> iterator2 = valueProviders.iterator();
        grid.getColumns().forEach(column -> {
            TextField field = new TextField();
            ValueProvider<Report, String> valueProvider = iterator2.next();
            field.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
                @Override
                public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
                    ((ListDataProvider<Report>)grid.getDataProvider()).addFilter(report -> StringUtils.containsIgnoreCase(valueProvider.apply(report), field.getValue()));
                }
            });
            field.setValueChangeMode(ValueChangeMode.EAGER);
            filterRow.getCell(column).setComponent(field);
            field.setSizeFull();
            field.setPlaceholder("Filter");
        });*/
        add(grid);
        setSizeFull();
        grid.setSizeFull();
        updateList();
        grid.setColumnReorderingAllowed(Boolean.TRUE);
        //Listeners for actions
        newBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                reportDialog.init();
                reportDialog.open();
                reportDialog.setChangeHandler(new ReportDialog.ChangeHandler() {
                    @Override
                    public void onChange() {
                        updateList();
                    }
                });
            }
        });
        refreshBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                updateList();
            }
        });
    }

    /**
     * Update List
     */
    private void updateList() {
        grid.setItems(reportService.getAll());
    }

    public void setReportDialog(ReportDialog reportDialog) {
        this.reportDialog = reportDialog;
    }
}
