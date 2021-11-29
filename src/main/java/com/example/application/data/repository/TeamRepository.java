package com.example.application.data.repository;

import com.example.application.data.entity.Stadium;
import com.example.application.data.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, String> {
    @Query("select c from Team c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.city) like lower(concat('%', :searchTerm, '%'))")
    List<Team> search(@Param("searchTerm") String searchTerm);

    Team findByStadium(Stadium theStadium);

    @Transactional
    @Modifying
    @Query("update Team t set t.stadium = null where t.name = ?1")
    void setTeamByName(String name);
}