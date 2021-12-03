package com.example.application.views.participatingList;

import com.example.application.components.NotificationComponent;
import com.example.application.data.entity.*;
import com.example.application.views.refereeingList.RefereeingForm;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ParticipatingForm extends FormLayout {
    Binder<Participating> binder = new BeanValidationBinder<>(Participating.class);

    ComboBox<ClubEmployee> pesel = new ComboBox<>("Pesel");
    ComboBox<Match> matchId = new ComboBox<>("Match id");
    IntegerField goals = new IntegerField("Goals");
    IntegerField assists = new IntegerField("Assists");

    ComboBox<String> gotYellowCard = new ComboBox<>("Got yellow card?");
    ComboBox<String> gotRedCard = new ComboBox<>("Got red card?");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Participating participating;

    public ParticipatingForm(List<ClubEmployee> clubEmployees, List<Match> matches) {
        addClassName("participating-form");

        binder.bindInstanceFields(this);

        matchId.setItems(matches);
        matchId.setItemLabelGenerator(Match::getMatchIdString);

        pesel.setItems(clubEmployees);
        pesel.setItemLabelGenerator(ClubEmployee::getPesel);

        gotYellowCard.setItems("Yes", "No");
        gotYellowCard.setValue("No");
        gotYellowCard.setLabel("Got yellow card?");

        gotRedCard.setItems("Yes", "No");
        gotRedCard.setValue("No");
        gotRedCard.setLabel("Got red card?");

        add(matchId, pesel, goals, assists, gotYellowCard, gotRedCard, createButtonLayout());
    }

    public void setParticipating(Participating participating){
        this.participating = participating;
        binder.readBean(participating);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, participating)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(participating);
            fireEvent(new SaveEvent(this, participating));
            NotificationComponent notification = new NotificationComponent("Participating saved");
            notification.getSucessNotification().open();

        } catch (ValidationException e){
            e.printStackTrace();
            NotificationComponent notification = new NotificationComponent(e.toString());
            notification.getErrorNotification().open();
        }
    }

    // Events
    public static abstract class ParticipatingFormEvent extends ComponentEvent<ParticipatingForm> {
        private Participating participating;

        protected ParticipatingFormEvent(ParticipatingForm source, Participating participating) {
            super(source, false);
            this.participating = participating;
        }

        public Participating getParticipating() {
            return participating;
        }
    }

    public static class SaveEvent extends ParticipatingFormEvent {
        SaveEvent(ParticipatingForm source, Participating participating) {
            super(source, participating);
        }
    }

    public static class DeleteEvent extends ParticipatingFormEvent {
        DeleteEvent(ParticipatingForm source, Participating participating) {
            super(source, participating);
            new Notification("Participating deleted", 3000).open();
        }

    }

    public static class CloseEvent extends ParticipatingFormEvent {
        CloseEvent(ParticipatingForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
