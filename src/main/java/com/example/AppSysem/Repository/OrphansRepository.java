package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Orphans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrphansRepository extends JpaRepository<Orphans,Integer> {
    List<Orphans> findByStatus(String accepted);
}
