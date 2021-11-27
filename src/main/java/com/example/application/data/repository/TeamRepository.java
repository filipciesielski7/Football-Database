package com.example.application.data.repository;

import com.example.application.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {
    @Query("select c from Team c " +
            "where lower(c.Name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.City) like lower(concat('%', :searchTerm, '%'))")
    List<Team> search(@Param("searchTerm") String searchTerm);
}
