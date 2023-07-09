package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.ApplicationRepository;
import com.example.AppSysem.Repository.OrphansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrphansServices {
    @Autowired
    private OrphansRepository orphansRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public Orphans addOrphans(Orphans orphans){

        return this.orphansRepository.save(orphans);
    }

    public List<Orphans> getAllOrphans(){

        return this.orphansRepository.findAll();
    }


    public Orphans findOrphansById(Integer id){
        return this.applicationRepository.findOrphansById(id)
                .orElseThrow(()-> new ResourceNotFoundException("that id"+id+"not found"));
    }

    public void deleteOrphans(Integer id){
        this.applicationRepository.deleteOrphansdById(id);
    }
    public Orphans updateOrphans(Orphans orphans){
        Orphans orphans1 = applicationRepository.findOrphansById(orphans.getId()).orElse(null);
        orphans1.setDate(orphans.getDate());
        orphans1.setDistrict(orphans.getDistrict());
        orphans1.setFileData(orphans.getFileData());
        orphans1.setImageData(orphans.getImageData());
        orphans1.setRegion(orphans.getRegion());
        orphans1.setShehia(orphans.getShehia());
        orphans1.setStreet(orphans.getStreet());
        orphans1.setDistrict(orphans.getDistrict());
        orphans1.setStatus(orphans.getStatus());
        orphans1.setHouseNo(orphans.getHouseNo());
        orphans1.setOrp_firstName(orphans.getOrp_firstName());
        orphans1.setOrp_lasttName(orphans.getOrp_lasttName());
        orphans1.setOrp_sectName(orphans.getOrp_sectName());
        orphans1.setOrp_phone(orphans.getOrp_phone());
        return applicationRepository.save(orphans1);
    }
}
