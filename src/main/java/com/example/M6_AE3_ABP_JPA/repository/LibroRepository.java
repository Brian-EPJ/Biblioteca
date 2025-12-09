package com.example.M6_AE3_ABP_JPA.repository;

import com.example.M6_AE3_ABP_JPA.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
