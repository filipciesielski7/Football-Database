package com.example.application.views.participatingList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.Participating;
import com.example.application.data.entity.Refereeing;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.refereeingList.RefereeingForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Participating | Football")
@Route(value = "participating", layout = MainLayout.class)
public class ParticipatingView extends VerticalLayout {
    Grid<Participating> grid = new Grid<>(Participating.class);
    TextField filterText = new TextField();
    ParticipatingForm form;
    private CrmService service;

    public ParticipatingView(CrmService service) {
        this.service = service;
        addClassName("participating-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setParticipating(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllParticipating(filterText.getValue()));
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
        form = new ParticipatingForm(service.findAllClubEmployees(), service.findAllMatches());
        form.setWidth("25em");

        form.addListener(ParticipatingForm.SaveEvent.class, this::saveParticipating);
        form.addListener(ParticipatingForm.DeleteEvent.class, this::deleteParticipating);
        form.addListener(ParticipatingForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteParticipating(ParticipatingForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getParticipating().getPesel().getFirstName() + " " + event.getParticipating().getPesel().getLastName() + " participating in match " + event.getParticipating().getMatchId().getMatchIdString());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteParticipating(event.getParticipating());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveParticipating(ParticipatingForm.SaveEvent event) {
        service.saveParticipating(event.getParticipating());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by match id or pesel");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addParticipatingButton = new Button("Add participating");
        addParticipatingButton.addClickListener(e -> addParticipating());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addParticipatingButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addParticipating() {
        grid.asSingleSelect().clear();
        editParticipating(new Participating());
    }

    private void configureGrid() {
        grid.addClassName("participating-grid");
        grid.setSizeFull();
        grid.setColumns("goals", "assists", "gotYellowCard", "gotRedCard");
        grid.addColumn(participating -> participating.getMatchId().getMatchIdString()).setHeader("Match Id");
        grid.addColumn(participating-> participating.getPesel().getPesel()).setHeader("Pesel");
//        grid.addColumn(refereeing-> refereeing.getPesel().getFirstName()).setHeader("First Name");
//        grid.addColumn(refereeing-> refereeing.getPesel().getLastName()).setHeader("Last Name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editParticipating(e.getValue()));
    }

    private void editParticipating(Participating participating) {
        if(participating == null) {
            closeEditor();
        }else{
            form.setParticipating(participating);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}