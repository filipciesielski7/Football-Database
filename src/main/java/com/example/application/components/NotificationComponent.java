package com.example.application.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationComponent {
    private Notification notification;
    private String message;

    public NotificationComponent(String message) {
        this.notification = new Notification(message, 3000);
        this.message = message;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification getSucessNotification(){
        this.notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        Icon icon = VaadinIcon.CHECK_CIRCLE.create();
        Div info = new Div(new Text(this.message));
        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.notification.add(layout);
        this.notification.setPosition(Notification.Position.TOP_END);

        return notification;
    }

    public Notification getErrorNotification(){
        this.notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        Icon icon = VaadinIcon.WARNING.create();
        Div info = new Div(new Text(message));
        HorizontalLayout layout = new HorizontalLayout(icon, info);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        this.notification.add(layout);
        this.notification.setPosition(Notification.Position.TOP_END);

        return notification;
    }
}
