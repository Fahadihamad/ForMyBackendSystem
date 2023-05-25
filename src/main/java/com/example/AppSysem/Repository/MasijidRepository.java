package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Massjid_build;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasijidRepository extends JpaRepository<Massjid_build,Integer> {


    List<Massjid_build> findByStatus(String accepted);
}
