package com.example.application.data.repository;

import com.example.application.data.entity.TopAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopAttendanceRepository extends JpaRepository<TopAttendance, Integer> {
    @Query("select c from TopAttendance c " +
            "where lower(c.league) like lower(concat('%', :searchTerm, '%'))")
    List<TopAttendance> search(@Param("searchTerm") String searchTerm);
}
