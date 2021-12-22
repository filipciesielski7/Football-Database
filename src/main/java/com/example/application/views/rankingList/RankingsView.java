package com.example.application.views.rankingList;

import com.example.application.data.entity.Ranking;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Rankings | Football")
@Route(value = "rankings", layout = MainLayout.class)
public class RankingsView extends VerticalLayout {
    private CrmService service;
    Grid<Ranking> grid = new Grid<>(Ranking.class);
    ComboBox<String> comboBox = new ComboBox<>("League season");
    TextField filterText = new TextField();

    public RankingsView(CrmService service) {
        this.service = service;
        addClassName("rankings-view");
        setSizeFull();

        List<String> league_names = service.findAllLeagueSeasonsNames(null);
        league_names.add(0, "All Leagues");
        comboBox.setItems(league_names);
        comboBox.setPlaceholder("Filter by league name");
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "250px");

        filterText.setPlaceholder("Search by team name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        configureGrid();

        HorizontalLayout toolbar = new HorizontalLayout(comboBox, filterText);
        toolbar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        add(toolbar, getContent());

        comboBox.addValueChangeListener(event -> {
            if(event.getValue() == "All Leagues"){
                grid.setItems(service.findAllRanking("", filterText.getValue()));
            }
            else{
                grid.setItems(service.findAllRanking(event.getValue(), filterText.getValue()));
            }
        });

        updateList();
    }

    private void updateList() {
        if(comboBox.getValue() == "All Leagues" || comboBox.getValue() == "null") {
            grid.setItems(service.findAllRanking("", filterText.getValue()));
        }
        else {
            grid.setItems(service.findAllRanking(comboBox.getValue(), filterText.getValue()));
        }
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureGrid() {
        grid.addClassName("ranking-grid");
        grid.setSizeFull();
        grid.setColumns("name", "points", "wins", "draws", "loses", "scored", "conceded", "league");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
