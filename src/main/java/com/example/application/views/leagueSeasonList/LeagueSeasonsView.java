package com.example.application.views.leagueSeasonList;

import com.example.application.data.entity.LeagueSeason;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("League Seasons | Football")
@Route(value = "league-seasons", layout = MainLayout.class)
public class LeagueSeasonsView  extends VerticalLayout {
    Grid<LeagueSeason> grid = new Grid<>(LeagueSeason.class);
    TextField filterText = new TextField();
    LeagueSeasonForm form;
    private CrmService service;

    public LeagueSeasonsView(CrmService service) {
        this.service = service;
        addClassName("leagueSeasons-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setLeagueSeason(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllLeagueSeasons(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new LeagueSeasonForm();
        form.setWidth("25em");

        form.addListener(LeagueSeasonForm.SaveEvent.class, this::saveLeagueSeason);
        form.addListener(LeagueSeasonForm.DeleteEvent.class, this::deleteLeagueSeason);
        form.addListener(LeagueSeasonForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteLeagueSeason(LeagueSeasonForm.DeleteEvent event) {
        service.deleteLeagueSeason(event.getLeagueSeason());
        updateList();
        closeEditor();
    }

    private void saveLeagueSeason(LeagueSeasonForm.SaveEvent event) {
        service.saveLeagueSeason(event.getLeagueSeason());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by league season name and year");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addLeagueSeasonButton = new Button("Add league season");
        addLeagueSeasonButton.addClickListener(e -> addLeagueSeason());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addLeagueSeasonButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addLeagueSeason() {
        grid.asSingleSelect().clear();
        editLeagueSeason(new LeagueSeason());
    }

    private void configureGrid() {
        grid.addClassName("leagueSeasons-grid");
        grid.setSizeFull();
        grid.setColumns("name", "year", "division");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editLeagueSeason(e.getValue()));
    }

    private void editLeagueSeason(LeagueSeason leagueSeason) {
        if(leagueSeason == null) {
            closeEditor();
        }else{
            form.setLeagueSeason(leagueSeason);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}