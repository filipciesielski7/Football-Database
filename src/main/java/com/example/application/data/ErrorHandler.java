package com.example.application.data;

public class ErrorHandler {
    public ErrorHandler() {
    }

    public String errorTranslator(String error){
        if(error.equals("com.vaadin.flow.data.binder.ValidationException: Validation has failed for some fields")){
            return "Fill in all required fields";
        }
        else if(error.equals("com.vaadin.flow.data.binder.BindingException: An exception has been thrown inside binding logic for the field element [label='Capacity']")){
            return "Capacity can't be empty";
        }
        else if(error.equals("com.vaadin.flow.data.binder.BindingException: An exception has been thrown inside binding logic for the field element [label='Salary']")){
            return "Salary can't be empty";
        }
        else if(error.equals("Cannot invoke \"com.example.application.data.entity.Stadium.getCapacity()\" because the return value of \"com.vaadin.flow.component.combobox.ComboBox.getValue()\" is null")){
            return "Stadium can't be empty";
        }
        else if(error.equals("org.springframework.orm.jpa.JpaSystemException: No part of a composite identifier may be null; nested exception is org.hibernate.HibernateException: No part of a composite identifier may be null")){
            return "Fill in all required fields";
        }

        return error;
    }
}
