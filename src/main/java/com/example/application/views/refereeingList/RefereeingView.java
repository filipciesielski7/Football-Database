package com.example.application.views.refereeingList;

import com.example.application.components.ConfirmDialogComponent;
import com.example.application.data.entity.Refereeing;
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

@PageTitle("Refereeing | Football")
@Route(value = "refereeing", layout = MainLayout.class)
public class RefereeingView extends VerticalLayout {
    Grid<Refereeing> grid = new Grid<>(Refereeing.class);
    TextField filterText = new TextField();
    RefereeingForm form;
    private CrmService service;

    public RefereeingView(CrmService service) {
        this.service = service;
        addClassName("refereeing-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(getToolbar(), getContent());

        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setRefereeing(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllRefereeing(filterText.getValue()));
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
        form = new RefereeingForm(service.findAllLeagueReferees("Referee"), service.findAllMatches());
        form.setWidth("25em");

        form.addListener(RefereeingForm.SaveEvent.class, this::saveRefereeing);
        form.addListener(RefereeingForm.DeleteEvent.class, this::deleteRefereeing);
        form.addListener(RefereeingForm.CloseEvent.class, e -> closeEditor());
    }

    private void deleteRefereeing(RefereeingForm.DeleteEvent event) {
        ConfirmDialogComponent dialog = new ConfirmDialogComponent(event.getRefereeing().getMatchId().getMatchId() + " match refereeing");
        dialog.getDeleteConfirmDialog().addConfirmListener(event2 -> {
            service.deleteRefereeing(event.getRefereeing());
            updateList();
            closeEditor();
        });
        dialog.getDeleteConfirmDialog().open();
    }

    private void saveRefereeing(RefereeingForm.SaveEvent event) {
        service.saveRefereeing(event.getRefereeing());
        updateList();
        closeEditor();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by match id or pesel");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addRefereeingButton = new Button("Add refereeing");
        addRefereeingButton.addClickListener(e -> addRefereeing());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addRefereeingButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRefereeing() {
        grid.asSingleSelect().clear();
        editRefereeing(new Refereeing());
    }

    private void configureGrid() {
        grid.addClassName("refereeing-grid");
        grid.setSizeFull();
        grid.setColumns("function");
        grid.addColumn(refereeing -> refereeing.getMatchId().getMatchIdString()).setHeader("Match Id");
        grid.addColumn(refereeing -> refereeing.getPesel().getPesel()).setHeader("Pesel");
        grid.addColumn(refereeing -> refereeing.getPesel().getFirstName()).setHeader("First Name");
        grid.addColumn(refereeing -> refereeing.getPesel().getLastName()).setHeader("Last Name");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editRefereeing(e.getValue()));
    }

    private void editRefereeing(Refereeing refereeing) {
        if(refereeing == null) {
            closeEditor();
        }else{
            form.setRefereeing(refereeing);
            form.setVisible(true);
            addClassName("editing");
        }
    }
}
