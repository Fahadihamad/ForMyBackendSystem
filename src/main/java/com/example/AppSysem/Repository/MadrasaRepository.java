package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Madrasa_build;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MadrasaRepository extends JpaRepository<Madrasa_build,Integer> {
    List<Madrasa_build> findByStatus(String accepted);
}
