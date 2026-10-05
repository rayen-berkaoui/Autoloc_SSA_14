package com.example.autoloc.repository;

import com.example.autoloc.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByAgenceIdAgence(Long idAgence);
}
