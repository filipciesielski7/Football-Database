package com.example.application.views;

import com.example.application.views.clubEmployeeList.ClubEmployeesView;
import com.example.application.views.leagueEmployeeList.LeagueEmployeesView;
import com.example.application.views.leagueSeasonList.LeagueSeasonsView;
import com.example.application.views.matchList.MatchesView;
import com.example.application.views.participatingList.ParticipatingView;
import com.example.application.views.rankingList.RankingsView;
import com.example.application.views.refereeingList.RefereeingView;
import com.example.application.views.stadiumList.StadiumsView;
import com.example.application.views.teamList.TeamsView;
import com.example.application.views.topAttendanceList.TopAttendancesView;
import com.example.application.views.topPlayerList.TopPlayersView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    public MainLayout(){
        createHeader();
        createDrawer();
    }

    private void createDrawer() {
        RouterLink homeView = new RouterLink("Home", homeView.class);
        homeView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink rankingView = new RouterLink("Rankings", RankingsView.class);
        rankingView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink topPlayerView = new RouterLink("Top Players", TopPlayersView.class);
        topPlayerView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink topAttendanceView = new RouterLink("Top Attendances", TopAttendancesView.class);
        topAttendanceView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink teamsView = new RouterLink("Teams", TeamsView.class);
        teamsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink stadiumsView = new RouterLink("Stadiums", StadiumsView.class);
        stadiumsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink leagueSeasonsView = new RouterLink("League Seasons", LeagueSeasonsView.class);
        leagueSeasonsView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink clubEmployeesView = new RouterLink("Club Employees", ClubEmployeesView.class);
        clubEmployeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink leagueEmployeesView = new RouterLink("League Employees", LeagueEmployeesView.class);
        leagueEmployeesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink matchesView = new RouterLink("Matches", MatchesView.class);
        matchesView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink refereeingView = new RouterLink("Refereeing", RefereeingView.class);
        refereeingView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink participatingView = new RouterLink("Participating", ParticipatingView.class);
        participatingView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(homeView,
                rankingView,
                topPlayerView,
                topAttendanceView,
                teamsView,
                stadiumsView,
                leagueSeasonsView,
                clubEmployeesView,
                leagueEmployeesView,
                matchesView,
                refereeingView,
                participatingView));
    }

    private void createHeader() {
        H1 logo = new H1("Football Database");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}
