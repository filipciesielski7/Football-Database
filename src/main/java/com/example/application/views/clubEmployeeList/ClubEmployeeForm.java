package com.example.application.views.clubEmployeeList;

import com.example.application.data.entity.ClubEmployee;
import com.example.application.data.entity.Team;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ClubEmployeeForm extends FormLayout {

    Binder<ClubEmployee> binder = new BeanValidationBinder<>(ClubEmployee.class);

    TextField pesel = new TextField("Pesel");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    IntegerField salary = new IntegerField("Salary");
    DatePicker dateOfBirth = new DatePicker("Date of Birth");

    ComboBox<String> role = new ComboBox<>("Role");

    TextField position = new TextField("Position");
    TextField function = new TextField("Function");

    ComboBox<Team> team = new ComboBox<>("Team");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private ClubEmployee clubEmployee;

    public ClubEmployeeForm(List<Team> teams) {
        addClassName("ClubEmployees-form");

        binder.forField(dateOfBirth).withConverter(new LocalDateToDateConverter()).bind(ClubEmployee::getDateOfBirth, ClubEmployee::setDateOfBirth);
        binder.bindInstanceFields(this);

        role.setItems("Player", "Trainer", "Employee");
        role.setValue("Player");
        role.setLabel("Role");

        team.setItems(teams);
        team.setItemLabelGenerator(Team::getName);

        add(pesel, firstName, lastName, salary, dateOfBirth, role, position, function, team, createButtonLayout());
    }

    public void setClubEmployee(ClubEmployee clubEmployee){
        this.clubEmployee = clubEmployee;
        binder.readBean(clubEmployee);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, clubEmployee)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(clubEmployee);
            fireEvent(new SaveEvent(this, clubEmployee));
        } catch (ValidationException e){
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ClubEmployeeFormEvent extends ComponentEvent<ClubEmployeeForm> {
        private ClubEmployee clubEmployee;

        protected ClubEmployeeFormEvent(ClubEmployeeForm source, ClubEmployee clubEmployee) {
            super(source, false);
            this.clubEmployee = clubEmployee;
        }

        public ClubEmployee getClubEmployee() {
            return clubEmployee;
        }
    }

    public static class SaveEvent extends ClubEmployeeFormEvent {
        SaveEvent(ClubEmployeeForm source, ClubEmployee clubEmployee) {
            super(source, clubEmployee);
        }
    }

    public static class DeleteEvent extends ClubEmployeeFormEvent {
        DeleteEvent(ClubEmployeeForm source, ClubEmployee clubEmployee) {
            super(source, clubEmployee);
        }

    }

    public static class CloseEvent extends ClubEmployeeFormEvent {
        CloseEvent(ClubEmployeeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}