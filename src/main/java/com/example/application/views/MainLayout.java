package com.example.application.views;

import com.example.application.views.stadiumList.StadiumsView;
import com.example.application.views.teamList.TeamsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink homeView = new RouterLink("Home", homeView.class);
        homeView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink teamsView = new RouterLink("Teams", TeamsView.class);
        teamsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink stadiumsView = new RouterLink("Stadiums", StadiumsView.class);
        stadiumsView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(homeView, teamsView, stadiumsView));
    }

    private void createHeader() {
        H1 logo = new H1("Football Database");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}
