package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.ApplicationRepository;
import com.example.AppSysem.Repository.MasijidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasjidBuildService {
    @Autowired
   public ApplicationRepository applicationRepository;
    @Autowired
    public MasijidRepository masijidRepository;

    public Massjid_build addMasjid(Massjid_build massjid_build){

        return this.masijidRepository.save(massjid_build);
    }

    public List<Massjid_build> getAllMasjid(){
        return this.masijidRepository.findAll();
    }


    public Massjid_build findMasjidById(Integer id){
        return this.applicationRepository.findMasjidById(id)
                .orElseThrow(()-> new ResourceNotFoundException("that id"+id+"not found"));
    }

    public void deleteMasjid(Integer id){
        this.applicationRepository.deleteMasjidById(id);
    }
    public Massjid_build updateMasjid(Massjid_build massjid_build){
        Massjid_build existingMasjid = applicationRepository.findMasjidById(massjid_build.getId()).orElse(null);
        existingMasjid.setAssisst_phone(massjid_build.getAssisst_phone());
        existingMasjid.setDate(massjid_build.getDate());
        existingMasjid.setDistrict(massjid_build.getDistrict());
        existingMasjid.setFileData(massjid_build.getFileData());
        existingMasjid.setImageData(massjid_build.getImageData());
        existingMasjid.setRegion(massjid_build.getRegion());
        existingMasjid.setShehia(massjid_build.getShehia());
        existingMasjid.setStreet(massjid_build.getStreet());
        existingMasjid.setDistrict(massjid_build.getDistrict());
        existingMasjid.setAssist_firstName(massjid_build.getAssist_firstName());
        existingMasjid.setAssist_secName(massjid_build.getAssist_secName());
        existingMasjid.setAssist_lastName(massjid_build.getAssist_lastName());
        existingMasjid.setImam_firstName(massjid_build.getImam_firstName());
        existingMasjid.setImam_lasttName(massjid_build.getImam_lasttName());
        existingMasjid.setImam_phone(massjid_build.getImam_phone());
        existingMasjid.setImam_sectName(massjid_build.getImam_sectName());
        existingMasjid.setStatus(massjid_build.getStatus());
        return applicationRepository.save(existingMasjid);
    }
}
