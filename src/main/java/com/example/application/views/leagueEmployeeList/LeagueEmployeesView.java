package com.example.application.views.leagueEmployeeList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.LeagueEmployee;
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

@PageTitle("League Employees | Football")
@Route(value = "league-employees", layout = MainLayout.class)
public class LeagueEmployeesView extends VerticalLayout {
    Grid<LeagueEmployee> grid = new Grid<>(LeagueEmployee.class);
    TextField filterText = new TextField();
    LeagueEmployeeForm form;
    private CrmService service;

    public LeagueEmployeesView(CrmService service) {
        this.service = service;
        addClassName("leagueEmployees-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setLeagueEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllLeagueEmployees(filterText.getValue()));
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
        form = new LeagueEmployeeForm();
        form.setWidth("25em");

        form.addListener(LeagueEmployeeForm.SaveEvent.class, this::saveLeagueEmployee);
        form.addListener(LeagueEmployeeForm.DeleteEvent.class, this::deleteLeagueEmployee);
        form.addListener(LeagueEmployeeForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteLeagueEmployee(LeagueEmployeeForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getLeagueEmployee().getFirstName() + " " + event.getLeagueEmployee().getLastName());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteLeagueEmployee(event.getLeagueEmployee());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveLeagueEmployee(LeagueEmployeeForm.SaveEvent event) {
        service.saveLeagueEmployee(event.getLeagueEmployee());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by league employee name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addLeagueEmployeeButton = new Button("Add league employee");
        addLeagueEmployeeButton.addClickListener(e -> addLeagueEmployee());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addLeagueEmployeeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addLeagueEmployee() {
        grid.asSingleSelect().clear();
        editLeagueEmployee(new LeagueEmployee());
    }

    private void configureGrid() {
        grid.addClassName("leagueEmployees-grid");
        grid.setSizeFull();
        grid.setColumns("pesel", "firstName", "lastName", "dateOfBirth", "role");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editLeagueEmployee(e.getValue()));
    }

    private void editLeagueEmployee(LeagueEmployee leagueEmployee) {
        if(leagueEmployee == null) {
            closeEditor();
        }else{
            form.setLeagueEmployee(leagueEmployee);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}