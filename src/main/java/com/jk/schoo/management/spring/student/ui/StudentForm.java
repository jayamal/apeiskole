/*
package com.jk.schoo.management.spring.student.ui;

import com.jk.schoo.management.spring.Properties;
import com.jk.schoo.management.spring.student.domain.Student;
import com.jk.schoo.management.spring.student.service.StudentService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

*/
/**
 * Created by jayamalk on 7/6/2018.
 *//*

@SpringComponent
@UIScope
public class StudentForm extends Dialog {

    private StudentService studentService;
    private TextField name = new TextField("Name");
    private Binder<Student> binder;
    private ChangeHandler changeHandler;
    private Button save;
    public interface ChangeHandler {
        void onChange();
    }
    @Autowired
    public StudentForm(StudentService studentService) {
        this.studentService = studentService;
    }

    public void init(){
        removeAll();

        save = new Button("Save");
        Button cancel = new Button("Cancel");
        binder = new Binder<>(Student.class);
        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
        add(name, buttons);
        name.setRequired(Boolean.TRUE);
        binder.bindInstanceFields(this);
        cancel.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                close();
            }
        });
        save.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                BinderValidationStatus<Student> validationStatus = binder.validate();
                if(validationStatus.isOk()) {
                    Student student = binder.getBean();
                    String action = null;
                    if(student.getId() == null){
                        student.setCreateDateTime(Calendar.getInstance().getTime());
                        action = "registered";
                    }else{
                        student.setLastUpdatedDateTime(Calendar.getInstance().getTime());
                        action = "updated";
                    }
                    studentService.update(student);
                    changeHandler.onChange();
                    Notification.show(student.getName() + ", " + action, Properties.NOTIFICATION_DELAY, Properties.NOTIFICATION_POSITION);
                    StudentForm.this.close();
                }else{
                    for(BindingValidationStatus<?> error : validationStatus.getFieldValidationErrors()){
                        Notification.show(error.getMessage().get());
                    }
                }
            }
        });
    }

    public void bind(Student student){
        binder.setBean(student);
        if(student.getId() == null){
            save.setText("Register");
        }else{
            save.setText("Update");
        }
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }
}
*/
