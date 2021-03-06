package com.example.application.views.leagueEmployeeList;

import com.example.application.components.NotificationComponent;
import com.example.application.data.ErrorHandler;
import com.example.application.data.entity.LeagueEmployee;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingException;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;
import org.springframework.orm.jpa.JpaSystemException;

public class LeagueEmployeeForm extends FormLayout {
    Binder<LeagueEmployee> binder = new BeanValidationBinder<>(LeagueEmployee.class);
    ErrorHandler errorHandler = new ErrorHandler();

    TextField pesel = new TextField("Pesel");
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    DatePicker dateOfBirth = new DatePicker("Date of Birth");

    ComboBox<String> role = new ComboBox<>("Role");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private LeagueEmployee leagueEmployee;

    public LeagueEmployeeForm() {
        addClassName("LeagueEmployees-form");

        binder.forField(dateOfBirth).withConverter(new LocalDateToDateConverter()).bind(LeagueEmployee::getDateOfBirth, LeagueEmployee::setDateOfBirth);
        binder.bindInstanceFields(this);

        role.setItems("Delegate", "Referee");
        role.setValue("Referee");
        role.setLabel("Role");

        add(pesel, firstName, lastName, dateOfBirth, role, createButtonLayout());
    }

    public void setLeagueEmployee(LeagueEmployee leagueEmployee){
        this.leagueEmployee = leagueEmployee;
        binder.readBean(leagueEmployee);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, leagueEmployee)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(leagueEmployee);
            fireEvent(new SaveEvent(this, leagueEmployee));
            NotificationComponent notification = new NotificationComponent("League employee saved");
            notification.getSucessNotification().open();
        } catch (ValidationException e){
            e.printStackTrace();
            NotificationComponent notification = new NotificationComponent(errorHandler.errorTranslator(e.toString()));
            notification.getErrorNotification().open();
        } catch (BindingException e){
            e.printStackTrace();
            NotificationComponent notification = new NotificationComponent(errorHandler.errorTranslator(e.toString()));
            notification.getErrorNotification().open();
        } catch (JpaSystemException e){
            e.printStackTrace();
            NotificationComponent notification = new NotificationComponent(errorHandler.errorTranslator(e.toString()));
            notification.getErrorNotification().open();
        }
    }

    // Events
    public static abstract class LeagueEmployeeFormEvent extends ComponentEvent<LeagueEmployeeForm> {
        private LeagueEmployee leagueEmployee;

        protected LeagueEmployeeFormEvent(LeagueEmployeeForm source, LeagueEmployee leagueEmployee) {
            super(source, false);
            this.leagueEmployee = leagueEmployee;
        }

        public LeagueEmployee getLeagueEmployee() {
            return leagueEmployee;
        }
    }

    public static class SaveEvent extends LeagueEmployeeFormEvent {
        SaveEvent(LeagueEmployeeForm source, LeagueEmployee leagueEmployee) {
            super(source, leagueEmployee);
        }
    }

    public static class DeleteEvent extends LeagueEmployeeFormEvent {
        DeleteEvent(LeagueEmployeeForm source, LeagueEmployee leagueEmployee) {
            super(source, leagueEmployee);
            new Notification("League employee deleted", 3000).open();
        }

    }

    public static class CloseEvent extends LeagueEmployeeFormEvent {
        CloseEvent(LeagueEmployeeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}