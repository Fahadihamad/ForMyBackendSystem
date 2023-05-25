package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Massjid_build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Optional<Massjid_build> findMasjidById(Integer id);

    void deleteMasjidById(Integer id);

//    Optional<Application> findApplicationById(Long id);
}
