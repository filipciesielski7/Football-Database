package com.example.application.data.repository;

import com.example.application.data.entity.ClubEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubEmployeeRepository extends JpaRepository<ClubEmployee, String> {
    @Query("select c from ClubEmployee c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<ClubEmployee> search(@Param("searchTerm") String searchTerm);

    List<ClubEmployee> findByRole(String theRole);
}