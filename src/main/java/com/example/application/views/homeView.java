package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Football Database")
@Route(value = "", layout = MainLayout.class)
public class homeView extends VerticalLayout {
    private CrmService service;

    public homeView(CrmService service) {
        this.service = service;
        addClassName("home-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        add(new H1("Football SQL Database"));
        add(getMatchStats(), getLeagueSeasonsChart());
    }

    private Component getMatchStats() {
        Span stats = new Span(Long.toString(service.countMatches()) + " matches played");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    private Component getLeagueSeasonsChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        service.findAllLeagueSeasons().forEach(leagueSeason -> {
            dataSeries.add(new DataSeriesItem(leagueSeason.getName(), leagueSeason.getMatchesCount()));
        });
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}