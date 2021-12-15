package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Football Database")
@Route(value = "", layout = MainLayout.class)
public class homeView extends VerticalLayout {
    private CrmService service;

    public homeView(CrmService service) {
        this.service = service;
        addClassName("home-view");
        setSizeFull();
        add(new H1("Football SQL Database"));
    }
}