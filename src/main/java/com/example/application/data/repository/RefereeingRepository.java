package com.example.application.data.repository;

import com.example.application.data.entity.Refereeing;
import com.example.application.data.entity.helper.RefereeingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RefereeingRepository extends JpaRepository<Refereeing, RefereeingId> {
    @Query("select c from Refereeing c " +
            "where lower(c.matchId) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.pesel) like lower(concat('%', :searchTerm, '%'))")
    List<Refereeing> search(@Param("searchTerm") String searchTerm);
}
