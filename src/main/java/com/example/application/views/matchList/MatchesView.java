package com.example.application.views.matchList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.ClubEmployee;
import com.example.application.data.entity.Match;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Matches | Football")
@Route(value = "matches", layout = MainLayout.class)
public class MatchesView extends VerticalLayout {
    Grid<Match> grid = new Grid<>(Match.class);
    ComboBox<String> comboBox = new ComboBox<>("League season");
    TextField filterText = new TextField();
    MatchForm form;
    private CrmService service;

    public MatchesView(CrmService service) {
        this.service = service;
        addClassName("matches-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        comboBox.addValueChangeListener(event -> {
            if(event.getValue() == "All Leagues"){
                grid.setItems(service.findAllMatches("", filterText.getValue()));
            }
            else{
                grid.setItems(service.findAllMatches(event.getValue(), filterText.getValue()));
            }
        });

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setMatch(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        if(comboBox.getValue() == "All Leagues" || comboBox.getValue() == "null") {
            grid.setItems(service.findAllMatches("", filterText.getValue()));
        }
        else {
            grid.setItems(service.findAllMatches(comboBox.getValue(), filterText.getValue()));
        }
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
        form = new MatchForm(service.findAllTeams(), service.findAllStadiums(), service.findAllLeagueSeasons(), service.findAllLeagueDelegates("Delegate"));
        form.setWidth("25em");

        form.addListener(MatchForm.SaveEvent.class, this::saveMatch);
        form.addListener(MatchForm.DeleteEvent.class, this::deleteMatch);
        form.addListener(MatchForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteMatch(MatchForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getMatch().getHomeTeam().getName() + " vs " + event.getMatch().getAwayTeam().getName());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteMatch(event.getMatch());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveMatch(MatchForm.SaveEvent event) {
        service.saveMatch(event.getMatch());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        List<String> league_names = service.findAllLeagueSeasonsNames(null);
        league_names.add(0, "All Leagues");
        comboBox.setItems(league_names);
        comboBox.setPlaceholder("Filter by league name");
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "250px");

        filterText.setPlaceholder("Filter by home or away team");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addMatchButton = new Button("Add match");
        addMatchButton.addClickListener(e -> addMatch());

        HorizontalLayout toolbar = new HorizontalLayout(comboBox, filterText, addMatchButton);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addMatch() {
        grid.asSingleSelect().clear();
        editMatch(new Match());
    }

    private void configureGrid() {
        grid.addClassName("matches-grid");
        grid.setSizeFull();
        grid.setColumns("matchId", "matchDate", "homeGoals", "awayGoals", "fansNumber");
        grid.addColumn(match -> match.getHomeTeam().getName()).setHeader("Home Team");
        grid.addColumn(match -> match.getAwayTeam().getName()).setHeader("Away Team");
        grid.addColumn(match -> match.getStadium().getName()).setHeader("Stadium");
        grid.addColumn(match -> match.getLeagueSeason().getName()).setHeader("League Season");
        grid.addColumn(match -> match.getLeagueEmployee().getLastName()).setHeader("Delegate");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editMatch(e.getValue()));
    }

    private void editMatch(Match match) {
        if(match == null) {
            closeEditor();
        }else{
            form.setMatch(match);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}