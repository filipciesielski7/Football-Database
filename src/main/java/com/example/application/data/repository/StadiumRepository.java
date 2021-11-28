package com.example.application.data.repository;

import com.example.application.data.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StadiumRepository extends JpaRepository<Stadium, String> {
    @Query("select c from Stadium c " +
            "where lower(c.Name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.Capacity) like lower(concat('%', :searchTerm, '%'))")
    List<Stadium> search(@Param("searchTerm") String searchTerm);
}
