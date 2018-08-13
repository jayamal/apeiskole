package com.jk.schoo.management.spring.common.validation.core;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

public class ConditionalValidator implements ConstraintValidator<Conditional, Object> {

    private String selected;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(Conditional requiredIfChecked) {
        selected = requiredIfChecked.selected();
        required = requiredIfChecked.required();
        message = requiredIfChecked.message();
        values = requiredIfChecked.values();
    }

    @Override
    public boolean isValid(Object objectToValidate, ConstraintValidatorContext context) {
        Boolean valid = true;
        try {
            Object actualValue = BeanUtils.getProperty(objectToValidate, selected);
            if (Arrays.asList(values).contains(actualValue)) {
                for (String propName : required) {
                    Object requiredValue = BeanUtils.getProperty(objectToValidate, propName);
                    valid = requiredValue != null && !isEmpty(requiredValue);
                    System.out.println("value: " + "" + requiredValue);
                    if (!valid) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(message).addPropertyNode(propName).addConstraintViolation();
                    }
                }
            }
        } catch (IllegalAccessException e) {
            //log.error("Accessor method is not available for class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            //log.error("Field or method is not present on class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            //log.error("An exception occurred while accessing class : {}, exception : {}", objectToValidate.getClass().getName(), e);
            e.printStackTrace();
            return false;
        }
        return valid;
    }
}