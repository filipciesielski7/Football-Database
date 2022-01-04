package com.example.application.data.repository;

import com.example.application.data.entity.LeagueSeason;
import com.example.application.data.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason, String>{
    @Query("select c from LeagueSeason c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.year) like lower(concat('%', :searchTerm, '%'))")
    List<LeagueSeason> search(@Param("searchTerm") String searchTerm);

    @Query("select c.name from LeagueSeason c " +
            "where lower(c.name) like lower(concat(:searchTerm, '%'))")
    List<String> searchName(@Param("searchTerm") String searchTerm);

    @Query("select c.name from LeagueSeason c ")
    List<String> searchAllNames();
}