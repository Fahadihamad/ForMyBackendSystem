package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Entity.Personals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Optional<Massjid_build> findMasjidById(Integer id);

    void deleteMasjidById(Integer id);

    Optional<Orphans> findOrphansById(Integer id);

    void deleteOrphansdById(Integer id);

    Optional<Personals> getPersonalById(Integer id);

    void deletePersonalById(Integer id);

//    Optional<Application> findApplicationById(Long id);

}
