package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Sponsor;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SponsorService {
    @Autowired
    public SponsorRepository sponsorRepository;
    public Sponsor fetchStaffByUserName(String userName){
        return this.sponsorRepository.findStaffByUserName(userName);
    }
}
