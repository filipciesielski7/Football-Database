package com.example.application.data.repository;

import com.example.application.data.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("select c from Match c " +
            "where lower(c.homeTeam) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.awayTeam) like lower(concat('%', :searchTerm, '%'))")
    List<Match> search(@Param("searchTerm") String searchTerm);
}
