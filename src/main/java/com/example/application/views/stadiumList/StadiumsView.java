package com.example.application.views.stadiumList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.Stadium;
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

@PageTitle("Stadiums | Football")
@Route(value = "stadiums", layout = MainLayout.class)
public class StadiumsView extends VerticalLayout {
    Grid<Stadium> grid = new Grid<>(Stadium.class);
    TextField filterText = new TextField();
    StadiumForm form;
    private CrmService service;

    public StadiumsView(CrmService service) {
        this.service = service;
        addClassName("stadiums-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setStadium(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllStadiums(filterText.getValue()));
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
        form = new StadiumForm();
        form.setWidth("25em");

        form.addListener(StadiumForm.SaveEvent.class, this::saveStadium);
        form.addListener(StadiumForm.DeleteEvent.class, this::deleteStadium);
        form.addListener(StadiumForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteStadium(StadiumForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getStadium().getName());
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteStadium(event.getStadium());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveStadium(StadiumForm.SaveEvent event) {
        Stadium stadiumCopy = service.findAllStadiums().stream().filter(stadium -> event.getStadium().getName().equals(stadium.getName()))
                        .findAny().orElse(null);
        if(stadiumCopy != null && !stadiumCopy.toString().equals(event.getStadium().toString())){
            ConfirmDialogComponent dialog = new ConfirmDialogComponent(stadiumCopy.getName());
            dialog.getModifyConfirmDialog().addConfirmListener(event2 -> {
                service.saveStadium(event.getStadium());
                updateList();
                closeEditor();
            });
            dialog.getModifyConfirmDialog().open();
        }
        else if (stadiumCopy != null && stadiumCopy.toString().equals(event.getStadium().toString())) {
//            System.out.println("No changes");
        }
        else{
            service.saveStadium(event.getStadium());
        }
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by stadium name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addStadiumButton = new Button("Add stadium");
        addStadiumButton.addClickListener(e -> addStadium());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addStadiumButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addStadium() {
        grid.asSingleSelect().clear();
        editStadium(new Stadium());
    }

    private void configureGrid() {
        grid.addClassName("stadium-grid");
        grid.setSizeFull();
        grid.setColumns("name", "capacity", "hasLightning", "hasUnderSoilHeating");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editStadium(e.getValue()));
    }

    private void editStadium(Stadium stadium) {
        if(stadium == null) {
            closeEditor();
        }else{
            form.setStadium(stadium);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}