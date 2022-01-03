package com.example.application.views.matchList;

import com.example.application.components.NotificationComponent;
import com.example.application.data.ErrorHandler;
import com.example.application.data.entity.*;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingException;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.shared.Registration;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

public class MatchForm extends FormLayout {
    Binder<Match> binder = new BeanValidationBinder<>(Match.class);
    ErrorHandler errorHandler = new ErrorHandler();

    // IntegerField matchId = new IntegerField("Match Id");
    DatePicker matchDate = new DatePicker("Date of Match");
    IntegerField homeGoals = new IntegerField("Home Goals");
    IntegerField awayGoals = new IntegerField("Away Goals");
    IntegerField fansNumber = new IntegerField("Fans Number");

    ComboBox<Team> homeTeam = new ComboBox<>("Home Team");
    ComboBox<Team> awayTeam = new ComboBox<>("Away Team");
    ComboBox<Stadium> stadium = new ComboBox<>("Stadium");
    ComboBox<LeagueSeason> leagueSeason = new ComboBox<>("League Season");
    ComboBox<LeagueEmployee> leagueEmployee = new ComboBox<>("Delegate Last Name");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Match match;

    public MatchForm(List<Team> teams, List<Stadium> stadiums, List<LeagueSeason> leagueSeasons, List<LeagueEmployee> leagueEmployees) {
        addClassName("Matches-form");

        binder.forField(matchDate).withConverter(new LocalDateToDateConverter()).bind(Match::getMatchDate, Match::setMatchDate);
        binder.bindInstanceFields(this);

        homeTeam.setItems(teams);
        homeTeam.setItemLabelGenerator(Team::getName);

        awayTeam.setItems(teams);
        awayTeam.setItemLabelGenerator(Team::getName);

        stadium.setItems(stadiums);
        stadium.setItemLabelGenerator(Stadium::getName);

        leagueSeason.setItems(leagueSeasons);
        leagueSeason.setItemLabelGenerator(LeagueSeason::getName);

        leagueEmployee.setItems(leagueEmployees);
        leagueEmployee.setItemLabelGenerator(LeagueEmployee::getLastName);

        add(matchDate, homeGoals, awayGoals, fansNumber, homeTeam, awayTeam, stadium, leagueSeason, leagueEmployee, createButtonLayout());
    }

    public void setMatch(Match match){
        this.match = match;
        binder.readBean(match);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, match)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            if(fansNumber.getValue() > stadium.getValue().getCapacity()){
                throw new Exception(stadium.getValue().getName() + " has a capacity of only " + stadium.getValue().getCapacity());
            }
            binder.writeBean(match);
            fireEvent(new SaveEvent(this, match));
            NotificationComponent notification = new NotificationComponent("Match saved");
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
        } catch (Exception e) {
            e.printStackTrace();
            NotificationComponent notification = new NotificationComponent(errorHandler.errorTranslator(e.getMessage()));
            notification.getErrorNotification().open();
        }
    }

    // Events
    public static abstract class MatchFormEvent extends ComponentEvent<MatchForm> {
        private Match match;

        protected MatchFormEvent(MatchForm source, Match match) {
            super(source, false);
            this.match = match;
        }

        public Match getMatch() {
            return match;
        }
    }

    public static class SaveEvent extends MatchFormEvent {
        SaveEvent(MatchForm source, Match match) {
            super(source, match);
        }
    }

    public static class DeleteEvent extends MatchFormEvent {
        DeleteEvent(MatchForm source, Match match) {
            super(source, match);
            new Notification("Match deleted", 3000).open();
        }

    }

    public static class CloseEvent extends MatchFormEvent {
        CloseEvent(MatchForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}