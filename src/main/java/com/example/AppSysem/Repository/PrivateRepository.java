package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Personals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateRepository extends JpaRepository<Personals,Integer> {
    List<Personals> findByStatus(String accepted);
}
