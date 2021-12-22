package com.example.application.data.repository;

import com.example.application.data.entity.TopPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopPlayerRepository extends JpaRepository<TopPlayer, String> {
    @Query("select c from TopPlayer c " +
            "where lower(c.league) like lower(concat('%', :searchTerm, '%'))" +
            "and (lower(c.lastName) like lower(concat('%', :name, '%'))" +
            "or lower(c.firstName) like lower(concat('%', :name, '%')))")
    List<TopPlayer> search(@Param("searchTerm") String searchTerm,  @Param("name") String name);
}
