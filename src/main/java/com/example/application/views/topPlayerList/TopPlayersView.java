package com.example.application.views.topPlayerList;

import com.example.application.data.entity.TopPlayer;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Top Players | Football")
@Route(value = "top-players", layout = MainLayout.class)
public class TopPlayersView extends VerticalLayout {
    private CrmService service;
    Grid<TopPlayer> grid = new Grid<>(TopPlayer.class);
    ComboBox<String> comboBox = new ComboBox<>("League season");

    public TopPlayersView(CrmService service){
        this.service = service;
        addClassName("topPlayers-view"); setSizeFull();
        List<String> league_names = service.findAllLeagueSeasonsNames(null);
        league_names.add(0, "All Leagues");
        comboBox.setItems(league_names);
        comboBox.setPlaceholder("Filter by league name");
        comboBox.getStyle().set("--vaadin-combo-box-overlay-width", "250px");

        configureGrid();
        add(comboBox, getContent());

        comboBox.addValueChangeListener(event -> {
            if(event.getValue() == "All Leagues"){
                grid.setItems(service.findAllTopPlayer());
//                grid.setColumns("firstName", "lastName", "goals", "assists", "teamName", "league");
            }
            else{
                grid.setItems(service.findAllTopPlayer(event.getValue()));
//                grid.setColumns("firstName", "lastName", "goals", "assists", "teamName");
            }
        });

        updateList();
    }

    private void updateList() {
        grid.setItems(service.findAllTopPlayer(comboBox.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureGrid() {
        grid.addClassName("topPlayers-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "goals", "assists", "teamName", "league");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

}
