package com.example.application.views.stadiumList;

import com.example.application.data.entity.Stadium;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class StadiumForm extends FormLayout {
    Binder<Stadium> binder = new BeanValidationBinder<>(Stadium.class);

    TextField Name = new TextField("Stadium Name");
    IntegerField Capacity = new IntegerField("Capacity");
//    TextField hasLighting = new TextField("Has lighting?");
//    TextField hasUnderSoilHeating = new TextField("Has under-soil heating?");

    ComboBox<String> hasLightning = new ComboBox<>("Has lightning?");
    ComboBox<String> hasUnderSoilHeating = new ComboBox<>("Has under-soil heating?");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Stadium stadium;

    public StadiumForm() {
        addClassName("stadium-form");
        binder.bindInstanceFields(this);

        hasLightning.setItems("Yes", "No");
        hasLightning.setValue("No");
        hasLightning.setLabel("Has lightning?");

        hasUnderSoilHeating.setItems("Yes", "No");
        hasUnderSoilHeating.setValue("No");
        hasUnderSoilHeating.setLabel("Has under-soil heating?");

        add(Name, Capacity, hasLightning, hasUnderSoilHeating, createButtonLayout());
    }

    public void setStadium(Stadium stadium){
        this.stadium = stadium;
        binder.readBean(stadium);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, stadium)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(stadium);
            fireEvent(new SaveEvent(this, stadium));
        } catch (ValidationException e){
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class StadiumFormEvent extends ComponentEvent<StadiumForm> {
        private Stadium stadium;

        protected StadiumFormEvent(StadiumForm source, Stadium stadium) {
            super(source, false);
            this.stadium = stadium;
        }

        public Stadium getStadium() {
            return stadium;
        }
    }

    public static class SaveEvent extends StadiumFormEvent {
        SaveEvent(StadiumForm source, Stadium stadium) {
            super(source, stadium);
        }
    }

    public static class DeleteEvent extends StadiumFormEvent {
        DeleteEvent(StadiumForm source, Stadium stadium) {
            super(source, stadium);
        }

    }

    public static class CloseEvent extends StadiumFormEvent {
        CloseEvent(StadiumForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
