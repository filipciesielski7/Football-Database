package com.example.application.views.refereeingList;

import com.example.application.components.NotificationComponent;
import com.example.application.data.ErrorHandler;
import com.example.application.data.entity.LeagueEmployee;
import com.example.application.data.entity.Match;
import com.example.application.data.entity.Refereeing;
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
import com.vaadin.flow.data.binder.BindingException;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.List;

public class RefereeingForm extends FormLayout {
    Binder<Refereeing> binder = new BeanValidationBinder<>(Refereeing.class);
    ErrorHandler errorHandler = new ErrorHandler();

    ComboBox<LeagueEmployee> pesel = new ComboBox<>("League employee");
    ComboBox<Match> matchId = new ComboBox<>("Match");
    TextField function = new TextField("Function");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Refereeing refereeing;

    public RefereeingForm(List<LeagueEmployee> leagueEmployees, List<Match> matches) {
        addClassName("refereeing-form");

        binder.bindInstanceFields(this);

        matchId.setItems(matches);
        matchId.setItemLabelGenerator(Match::matchInfo);

        pesel.setItems(leagueEmployees);
        pesel.setItemLabelGenerator(LeagueEmployee::firstAndLastName);

        add(matchId, pesel, function, createButtonLayout());
    }

    public void setRefereeing(Refereeing refereeing){
        this.refereeing = refereeing;
        binder.readBean(refereeing);
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, refereeing)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try{
            binder.writeBean(refereeing);
            fireEvent(new SaveEvent(this, refereeing));
            NotificationComponent notification = new NotificationComponent("Refereeing saved");
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
    public static abstract class RefereeingFormEvent extends ComponentEvent<RefereeingForm> {
        private Refereeing refereeing;

        protected RefereeingFormEvent(RefereeingForm source, Refereeing refereeing) {
            super(source, false);
            this.refereeing = refereeing;
        }

        public Refereeing getRefereeing() {
            return refereeing;
        }
    }

    public static class SaveEvent extends RefereeingFormEvent {
        SaveEvent(RefereeingForm source, Refereeing refereeing) {
            super(source, refereeing);
        }
    }

    public static class DeleteEvent extends RefereeingFormEvent {
        DeleteEvent(RefereeingForm source, Refereeing refereeing) {
            super(source, refereeing);
            new Notification("Refereeing deleted", 3000).open();
        }

    }

    public static class CloseEvent extends RefereeingFormEvent {
        CloseEvent(RefereeingForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
