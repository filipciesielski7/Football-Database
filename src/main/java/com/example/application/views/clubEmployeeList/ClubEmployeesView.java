package com.example.application.views.clubEmployeeList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.ClubEmployee;
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

@PageTitle("Club Employees | Football")
@Route(value = "club-employees", layout = MainLayout.class)
public class ClubEmployeesView extends VerticalLayout {
    Grid<ClubEmployee> grid = new Grid<>(ClubEmployee.class);
    TextField filterText = new TextField();
    ClubEmployeeForm form;
    private CrmService service;

    public ClubEmployeesView(CrmService service) {
        this.service = service;
        addClassName("clubEmployees-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setClubEmployee(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllClubEmployees(filterText.getValue()));
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
        form = new ClubEmployeeForm(service.findAllTeams());
        form.setWidth("25em");

        form.addListener(ClubEmployeeForm.SaveEvent.class, this::saveClubEmployee);
        form.addListener(ClubEmployeeForm.DeleteEvent.class, this::deleteClubEmployee);
        form.addListener(ClubEmployeeForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteClubEmployee(ClubEmployeeForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getClubEmployee().getFirstName() + " " + event.getClubEmployee().getLastName());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteClubEmployee(event.getClubEmployee());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveClubEmployee(ClubEmployeeForm.SaveEvent event) {
        ClubEmployee clubEmployeeCopy = service.findAllClubEmployees().stream().filter(clubEmployee -> event.getClubEmployee().getPesel().equals(clubEmployee.getPesel()))
                .findAny().orElse(null);
        if(clubEmployeeCopy != null && !clubEmployeeCopy.toString().equals(event.getClubEmployee().toString())){
            ConfirmDialogComponent dialog = new ConfirmDialogComponent(clubEmployeeCopy.getFirstName() + " " + clubEmployeeCopy.getLastName());
            dialog.getModifyConfirmDialog().addConfirmListener(event2 -> {
                service.saveClubEmployee(event.getClubEmployee());
                updateList();
                closeEditor();
            });
            dialog.getModifyConfirmDialog().open();
        }
        else if (clubEmployeeCopy != null && clubEmployeeCopy.toString().equals(event.getClubEmployee().toString())) {
//            System.out.println("No changes");
        }
        else{
            service.saveClubEmployee(event.getClubEmployee());
        }
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by club employee name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addClubEmployeeButton = new Button("Add club employee");
        addClubEmployeeButton.addClickListener(e -> addClubEmployee());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addClubEmployeeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addClubEmployee() {
        grid.asSingleSelect().clear();
        editClubEmployee(new ClubEmployee());
    }

    private void configureGrid() {
        grid.addClassName("clubEmployees-grid");
        grid.setSizeFull();
        grid.setColumns("pesel", "firstName", "lastName", "salary", "dateOfBirth", "role", "position", "function");
        grid.addColumn(clubEmployee -> clubEmployee.getTeam() == null ? ' ' : clubEmployee.getTeam().getName()).setHeader("Team");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editClubEmployee(e.getValue()));
    }

    private void editClubEmployee(ClubEmployee clubEmployee) {
        if(clubEmployee == null) {
            closeEditor();
        }else{
            form.setClubEmployee(clubEmployee);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}