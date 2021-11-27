package com.example.application.data.repository;

import com.example.application.data.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
}
