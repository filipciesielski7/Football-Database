package com.example.application.data.repository;

import com.example.application.data.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, String> {
    @Query("select c from Ranking c " +
            "where lower(c.league) like lower(concat('%', :searchTerm, '%'))")
    List<Ranking> search(@Param("searchTerm") String searchTerm);
}
