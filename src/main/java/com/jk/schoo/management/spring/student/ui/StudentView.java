/*
package com.jk.schoo.management.spring.student.ui;

import com.jk.schoo.management.spring.Properties;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

*/
/**
 * Created by jayamalk on 7/5/2018.
 *//*

@SpringComponent
@UIScope
public class StudentView extends VerticalLayout{

    private Grid<Student> grid;
    private StudentService studentService;
    private StudentForm studentForm;

    @Autowired
    public StudentView(StudentService studentService, StudentForm studentForm) {
        this.studentService = studentService;
        this.studentForm = studentForm;

    }

    public void init(){
        removeAll();
        //Title
        Label label = new Label("Students");
        add(label);
        //Actions
        Button regBtn = new Button("Register");
        Button importBtn = new Button("Import");
        Button refreshBtn = new Button("Refresh");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        HorizontalLayout actionContainer = new HorizontalLayout();
        actionContainer.setWidth("100%");
        actionContainer.add(regBtn, importBtn, editBtn, deleteBtn, refreshBtn);
        add(actionContainer);
        //Grid
        grid = new Grid<>();

        List<ValueProvider<Student, String>> valueProviders = new ArrayList<>();
        valueProviders.add(student -> String.valueOf(student.getId()));
        valueProviders.add(Student::getName);
        valueProviders.add(Student::getBranch);
        valueProviders.add(student -> String.valueOf(student.getCreateDateTime()));
        valueProviders.add(student -> String.valueOf(student.getLastUpdatedDateTime()));

        Iterator<ValueProvider<Student, String>> iterator = valueProviders.iterator();

        grid.addColumn(iterator.next()).setHeader("ID");
        grid.addColumn(iterator.next()).setHeader("Name");
        grid.addColumn(iterator.next()).setHeader("Branch");
        grid.addColumn(iterator.next()).setHeader("Created On");
        grid.addColumn(iterator.next()).setHeader("Last Updated On");

        HeaderRow filterRow = grid.appendHeaderRow();

        Iterator<ValueProvider<Student, String>> iterator2 = valueProviders.iterator();

        grid.getColumns().forEach(column -> {
            TextField field = new TextField();
            ValueProvider<Student, String> valueProvider = iterator2.next();

            field.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
                @Override
                public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
                    ((ListDataProvider<Student>)grid.getDataProvider()).addFilter(student -> StringUtils.containsIgnoreCase(valueProvider.apply(student), field.getValue()));
                }
            });

            field.setValueChangeMode(ValueChangeMode.EAGER);

            filterRow.getCell(column).setComponent(field);
            field.setSizeFull();
            field.setPlaceholder("Filter");
        });

        add(grid);

        setSizeFull();
        grid.setSizeFull();

        updateList();

        //Listeners for actions
        deleteBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                for(Student item : grid.getSelectedItems()){
                    studentService.delete(item);
                    updateList();
                    Notification.show(item.getId() + " : " + item.getName() + ", deleted", Properties.NOTIFICATION_DELAY, Properties.NOTIFICATION_POSITION);
                }
            }
        });
        refreshBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                updateList();
            }
        });
        regBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                studentForm.init();
                studentForm.bind(new Student());
                studentForm.open();
            }
        });
        editBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                for(Student item : grid.getSelectedItems()){
                    studentForm.init();
                    studentForm.bind(item);
                    studentForm.open();
                    break;
                }
            }
        });
        studentForm.setChangeHandler(new StudentForm.ChangeHandler(){

            @Override
            public void onChange() {
                updateList();
            }
        });
    }

    */
/**
     * Update List
     *//*

    private void updateList() {
        grid.setItems(studentService.getAll());
    }

    */
/**
     * Update List
     *//*

    private void updateListAndFocusItem(Student student) {
        grid.setItems(studentService.getAll());
    }

}
*/
