package com.example.AppSysem.Repository;

import com.example.AppSysem.Entity.Sponsor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends CrudRepository<Sponsor,Integer> {
    Sponsor findStaffByUserName(String userName);
}
