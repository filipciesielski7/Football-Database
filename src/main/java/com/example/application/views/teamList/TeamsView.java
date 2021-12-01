package com.example.application.views.teamList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.Team;
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

@PageTitle("Teams | Football")
@Route(value = "teams", layout = MainLayout.class)
public class TeamsView  extends VerticalLayout{
    Grid<Team> grid = new Grid<>(Team.class);
    TextField filterText = new TextField();
    TeamForm form;
    private CrmService service;

    public TeamsView(CrmService service) {
        this.service = service;
        addClassName("teams-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setTeam(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllTeams(filterText.getValue()));
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
        form = new TeamForm(service.findAllStadiums());
        form.setWidth("25em");

        form.addListener(TeamForm.SaveEvent.class, this::saveTeam);
        form.addListener(TeamForm.DeleteEvent.class, this::deleteTeam);
        form.addListener(TeamForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteTeam(TeamForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getTeam().getName());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteTeam(event.getTeam());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveTeam(TeamForm.SaveEvent event) {
        service.saveTeam(event.getTeam());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by team name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addTeamButton = new Button("Add team");
        addTeamButton.addClickListener(e -> addTeam());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addTeamButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addTeam() {
        grid.asSingleSelect().clear();
        editTeam(new Team());
    }

    private void configureGrid() {
        grid.addClassName("team-grid");
        grid.setSizeFull();
        grid.setColumns("name", "city");
        grid.addColumn(team -> team.getStadium() == null ? ' ' : team.getStadium().getName()).setHeader("Stadium");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editTeam(e.getValue()));
    }

    private void editTeam(Team team) {
        if(team == null) {
            closeEditor();
        }else{
            form.setTeam(team);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}