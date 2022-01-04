-- DROP INDEX club_employees_idx;
-- DROP INDEX league_employees_idx;
-- DROP INDEX league_seasons_idx;
-- DROP INDEX league_seasons_name_idx;
-- DROP INDEX matches_idx;
-- DROP INDEX participating_idx;
-- DROP INDEX stadiums_idx;

-- DROP TABLE participating;
-- DROP TABLE refereeing;
-- DROP TABLE matches;
-- DROP TABLE league_employees;
-- DROP TABLE club_employees;
-- DROP TABLE league_seasons;
-- DROP TABLE teams;
-- DROP TABLE stadiums;

-- drop sequence match_seq;

-- ---------------------------------------------------

CREATE TABLE stadiums (
                          name varchar2(100 char) primary key,
                          capacity number(6) not null,
                          has_lightning varchar2(3 char) check(has_lightning in ('Yes', 'No')),
                          has_under_soil_heating varchar2(3 char) check(has_under_soil_heating in ('Yes', 'No'))
);

-- ---------------------------------------------------

CREATE TABLE teams (
                       name varchar2(40 char) primary key,
                       city varchar2(40 char) not null,
                       stadium_name varchar2(100 char) null,
                       constraint stadium_fk foreign key(stadium_name) references stadiums(name) on delete set null
);

-- ---------------------------------------------------

CREATE TABLE league_seasons(
                               name varchar2(100 char) primary key,
                               end_year number(4) not null,
                               division number(2) not null
);

-- ---------------------------------------------------

CREATE TABLE club_employees (
                                pesel varchar2(11 char) primary key,
                                first_name varchar2(20 char) not null,
                                last_name varchar2(30 char) not null,
                                salary number(6) not null,
                                date_of_birth date,
                                role varchar2(10 char) check (role in ('Player', 'Trainer', 'Employee')),
                                position varchar2(20 char),
                                function varchar2(20 char),
                                team_name varchar2(40 char) null,
                                constraint team_fk foreign key(team_name) references teams(name) on delete set null
);

-- ---------------------------------------------------

CREATE TABLE league_employees (
                                  pesel varchar2(11 char) primary key,
                                  first_name varchar2(20 char) not null,
                                  last_name varchar2(30 char) not null,
                                  date_of_birth date,
                                  role varchar2(10 char) check (role in ('Delegate', 'Referee'))
);

-- ---------------------------------------------------

CREATE TABLE matches (
                         match_id number(20) primary key,
                         match_date date,
                         goals_home_team number(4) not null,
                         goals_away_team number(4) not null,
                         fans_number number(6),
                         home_team varchar2(40 char) references teams(name) on delete cascade,
                         away_team varchar2(40 char) references teams(name) on delete cascade,
                         stadium_name varchar2(100 char) references stadiums(name) on delete cascade,
                         league_year varchar2(100 char) references league_seasons(name) on delete cascade,
                         delegate varchar2(11 char) references league_employees(pesel) on delete cascade
);

create sequence match_seq start with 1 increment by 1;

-- ---------------------------------------------------

CREATE TABLE refereeing (
                            pesel varchar2(11 char) references league_employees(pesel) on delete cascade,
                            match_id number(20) references matches(match_id) on delete cascade,
                            function varchar2(30 char) not null,
                            primary key(pesel, match_id)
);

-- ---------------------------------------------------

CREATE TABLE participating (
                               pesel varchar2(11 char) references club_employees(pesel) on delete cascade,
                               match_id number(20) references matches(match_id) on delete cascade,
                               goals number(3) not null,
                               assists number(3) not null,
                               got_yellow_card varchar2(3 char) check(got_yellow_card in ('Yes', 'No')),
                               got_red_card varchar2(3 char) check(got_red_card in ('Yes', 'No')),
                               primary key(pesel, match_id)
);

-- ---------------------------------------------------

CREATE OR REPLACE VIEW wins_as_home_team AS
SELECT teams.name AS name, matches.goals_home_team AS scored, matches.goals_away_team AS conceded, league_year
FROM teams INNER JOIN matches ON teams.name = matches.home_team
WHERE matches.goals_home_team > matches.goals_away_team;

CREATE OR REPLACE VIEW loses_as_home_team AS
SELECT teams.name AS name, matches.goals_home_team AS scored, matches.goals_away_team AS conceded, league_year
FROM teams INNER JOIN matches ON teams.name = matches.home_team
WHERE matches.goals_home_team < matches.goals_away_team;

CREATE OR REPLACE VIEW draws_as_home_team AS
SELECT teams.name AS name, matches.goals_home_team AS scored, matches.goals_away_team AS conceded, league_year
FROM teams INNER JOIN matches ON teams.name = matches.home_team
WHERE matches.goals_home_team = matches.goals_away_team;

CREATE OR REPLACE VIEW wins_as_away_team AS
SELECT teams.name AS name, matches.goals_home_team AS conceded, matches.goals_away_team AS scored, league_year
FROM teams INNER JOIN matches ON teams.name = matches.away_team
WHERE matches.goals_home_team < matches.goals_away_team;

CREATE OR REPLACE VIEW loses_as_away_team AS
SELECT teams.name AS name, matches.goals_home_team AS conceded, matches.goals_away_team AS scored, league_year
FROM teams INNER JOIN matches ON teams.name = matches.away_team
WHERE matches.goals_home_team > matches.goals_away_team;

CREATE OR REPLACE VIEW draws_as_away_team AS
SELECT teams.name AS name, matches.goals_home_team AS conceded, matches.goals_away_team AS scored, league_year
FROM teams INNER JOIN matches ON teams.name = matches.away_team
WHERE matches.goals_home_team = matches.goals_away_team;


CREATE OR REPLACE VIEW wins_home AS
SELECT name, COUNT(*) AS wins, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM wins_as_home_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW wins_away AS
SELECT name, COUNT(*) AS wins, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM wins_as_away_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW draws_home AS
SELECT name, COUNT(*) AS draws, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM draws_as_home_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW draws_away AS
SELECT name, COUNT(*) AS draws, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM draws_as_away_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW loses_home AS
SELECT name, COUNT(*) AS loses, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM loses_as_home_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW loses_away AS
SELECT name, COUNT(*) AS loses, SUM(scored) AS scored, SUM(conceded) AS conceded, league_year FROM loses_as_away_team GROUP BY league_year, name;

CREATE OR REPLACE VIEW wins AS
SELECT name, COALESCE(wins_home.wins, 0) + COALESCE(wins_away.wins, 0) AS wins,
       COALESCE(wins_home.scored, 0) + COALESCE(wins_away.scored, 0) AS scored,
       COALESCE(wins_home.conceded, 0) + COALESCE(wins_away.conceded, 0) AS conceded, wins_home.league_year
FROM wins_home FULL JOIN wins_away USING(name);

CREATE OR REPLACE VIEW draws AS
SELECT name, COALESCE(draws_home.draws, 0) + COALESCE(draws_away.draws, 0) AS draws,
       COALESCE(draws_home.scored, 0) + COALESCE(draws_away.scored, 0) AS scored,
       COALESCE(draws_home.conceded, 0) + COALESCE(draws_away.conceded, 0) AS conceded, draws_home.league_year
FROM draws_home FULL JOIN draws_away USING(name);

CREATE OR REPLACE VIEW loses AS
SELECT name, COALESCE(loses_home.loses, 0) + COALESCE(loses_away.loses, 0) AS loses,
       COALESCE(loses_home.scored, 0) + COALESCE(loses_away.scored, 0) AS scored,
       COALESCE(loses_home.conceded, 0) + COALESCE(loses_away.conceded, 0) AS conceded, loses_home.league_year
FROM loses_home FULL JOIN loses_away USING(name);

CREATE OR REPLACE VIEW ranking AS
SELECT name,
       COALESCE(wins.wins, 0) AS wins,
       COALESCE(loses.loses, 0) AS loses,
       COALESCE(draws.draws, 0) AS draws,
       COALESCE(wins.scored, 0) + COALESCE(loses.scored, 0) + COALESCE(draws.scored, 0) AS scored,
       COALESCE(wins.conceded, 0) + COALESCE(loses.conceded, 0) + COALESCE(draws.conceded, 0) AS conceded,
       COALESCE(wins.league_year, loses.league_year, draws.league_year) AS league
FROM wins FULL JOIN loses  USING(name) FULL JOIN draws USING(name);

CREATE OR REPLACE VIEW full_ranking AS
SELECT name,
       COALESCE(wins, 0)*3 + COALESCE(draws, 0) AS points,
       wins, draws, loses, scored, conceded, league
FROM ranking ORDER BY league, points DESC, name;

-- ---------------------------------------------------

CREATE OR REPLACE VIEW scored AS
SELECT pesel, first_name, last_name, match_id, goals, assists, team_name, matches.league_year AS league FROM participating NATURAL JOIN club_employees NATURAL JOIN matches WHERE role = 'Player';

CREATE OR REPLACE VIEW top_players AS
SELECT pesel, first_name, last_name, sum(goals) AS goals, sum(assists) AS assists, team_name, league  FROM scored GROUP BY pesel, league, first_name, last_name, team_name;

CREATE OR REPLACE VIEW top_players_ranking AS
SELECT * FROM top_players ORDER BY league, goals DESC, assists DESC, last_name, first_name;

-- ---------------------------------------------------

CREATE OR REPLACE VIEW attendance AS
SELECT match_id, stadium_name, fans_number, match_date, home_team, away_team, league_year AS league FROM matches;

CREATE OR REPLACE VIEW top_attendance AS
SELECT * FROM attendance ORDER BY fans_number DESC, stadium_name, match_date, home_team, away_team;

-- ---------------------------------------------------

CREATE INDEX club_employees_idx ON club_employees(first_name, last_name);
CREATE INDEX league_employees_idx ON league_employees(first_name, last_name);
CREATE INDEX league_seasons_idx ON league_seasons(name, end_year);
CREATE INDEX league_seasons_name_idx ON league_seasons(LOWER(name));
CREATE INDEX matches_idx ON matches(home_team, away_team, league_year);
CREATE INDEX participating_idx ON participating(match_id, pesel);
CREATE INDEX stadiums_idx ON stadiums(name, capacity);
CREATE INDEX teams_idx ON teams(name, city);

-- ---------------------------------------------------

CREATE OR REPLACE FUNCTION LeagueNameCount (
vleague_name IN VARCHAR)
RETURN NUMBER IS
vleague_count NUMBER;
BEGIN
SELECT COUNT(m.league_year) INTO vleague_count FROM MATCHES m WHERE m.league_year=vleague_name;
RETURN vleague_count;
END LeagueNameCount;
/
-- ---------------------------------------------------

CREATE OR REPLACE TRIGGER PayRise
    AFTER INSERT OR DELETE OR UPDATE ON participating
    FOR EACH ROW
BEGIN
CASE
        WHEN inserting THEN
            IF :new.goals >= 3 THEN
UPDATE club_employees SET salary=1.01*salary WHERE pesel=:new.pesel;
END IF;
WHEN updating THEN
            IF :old.goals < 3 AND :new.goals >= 3 THEN
UPDATE club_employees SET salary=1.01*salary WHERE pesel=:new.pesel;
ELSIF :old.goals >= 3 AND :new.goals < 3 THEN
UPDATE club_employees SET salary=salary/1.01 WHERE pesel=:new.pesel;
END IF;
WHEN deleting THEN
            IF :old.goals >= 3 THEN
update club_employees SET salary=salary/1.01 WHERE pesel=:new.pesel;
END IF;
END CASE;
END;

-- ---------------------------------------------------
