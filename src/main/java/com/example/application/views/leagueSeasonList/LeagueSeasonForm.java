package com.example.application.views.leagueSeasonList;

import com.example.application.data.entity.LeagueSeason;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LeagueSeasonForm  extends FormLayout {
    Binder<LeagueSeason> binder = new BeanValidationBinder<>(LeagueSeason.class);

    TextField Name = new TextField("Name and year");
    IntegerField Year = new IntegerField("Year");
    IntegerField Division = new IntegerField("Division");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private LeagueSeason leagueSeason;

    public LeagueSeasonForm () {
        addClassName("leagueSeasons-form");
        binder.bindInstanceFields(this);


        add(Name, Year, Division, createButtonLayout());
    }

    public void setLeagueSeason(LeagueSeason leagueSeason){
        this.leagueSeason = leagueSeason;
        binder.readBean(leagueSeason);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, leagueSeason)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(leagueSeason);
            fireEvent(new SaveEvent(this, leagueSeason));
            new Notification("League season saved", 3000).open();
        } catch (ValidationException e){
            e.printStackTrace();
            new Notification(e.toString(), 3000).open();
        }
    }

    // Events
    public static abstract class LeagueSeasonFormEvent extends ComponentEvent<LeagueSeasonForm> {
        private LeagueSeason leagueSeason;

        protected LeagueSeasonFormEvent(LeagueSeasonForm source, LeagueSeason leagueSeason) {
            super(source, false);
            this.leagueSeason = leagueSeason;
        }

        public LeagueSeason getLeagueSeason() {
            return leagueSeason;
        }
    }

    public static class SaveEvent extends LeagueSeasonFormEvent {
        SaveEvent(LeagueSeasonForm source, LeagueSeason leagueSeason) {
            super(source, leagueSeason);
        }
    }

    public static class DeleteEvent extends LeagueSeasonFormEvent {
        DeleteEvent(LeagueSeasonForm source, LeagueSeason leagueSeason) {
            super(source, leagueSeason);
            new Notification("League season deleted", 3000).open();
        }

    }

    public static class CloseEvent extends LeagueSeasonFormEvent {
        CloseEvent(LeagueSeasonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}