package com.example.application.data.repository;

import com.example.application.data.entity.Participating;
import com.example.application.data.entity.helper.ParticipatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipatingRepository extends JpaRepository<Participating, ParticipatingId> {
    @Query("select c from Participating c " +
            "where lower(c.matchId) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.pesel) like lower(concat('%', :searchTerm, '%'))")
    List<Participating> search(@Param("searchTerm") String searchTerm);
}
