package com.example.application.views.teamList;

import com.example.application.data.entity.Stadium;
import com.example.application.data.entity.Team;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class TeamForm extends FormLayout {
    Binder<Team> binder = new BeanValidationBinder<>(Team.class);

    TextField Name = new TextField("Team Name");
    TextField City = new TextField("City");

    ComboBox<Stadium> stadium = new ComboBox<>("Stadium");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Team team;

    public TeamForm(List<Stadium> stadiums) {
        addClassName("team-form");
        binder.bindInstanceFields(this);

        stadium.setItems(stadiums);
        stadium.setItemLabelGenerator(Stadium::getName);

        add(Name, City, stadium, createButtonLayout());
    }

    public void setTeam(Team team){
        this.team = team;
        binder.readBean(team);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, team)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(team);
            fireEvent(new SaveEvent(this, team));
            new Notification("Team saved", 3000).open();
        } catch (ValidationException e){
            e.printStackTrace();
            Notification notification = new Notification(e.toString(), 3000);
            notification.addThemeName("error");
            notification.open();
        }
    }

    // Events
    public static abstract class TeamFormEvent extends ComponentEvent<TeamForm> {
        private Team team;

        protected TeamFormEvent(TeamForm source, Team team) {
            super(source, false);
            this.team = team;
        }

        public Team getTeam() {
            return team;
        }
    }

    public static class SaveEvent extends TeamFormEvent {
        SaveEvent(TeamForm source, Team team) {
            super(source, team);
        }
    }

    public static class DeleteEvent extends TeamFormEvent {
        DeleteEvent(TeamForm source, Team team) {
            super(source, team);
            new Notification("Team deleted", 3000).open();
        }

    }

    public static class CloseEvent extends TeamFormEvent {
        CloseEvent(TeamForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}