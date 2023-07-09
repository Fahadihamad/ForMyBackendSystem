package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Repository.MadrasaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MadrasaBuildService {
  @Autowired
    private MadrasaRepository madrasaRepository;

    public Madrasa_build addMadrasa(Madrasa_build madrasa){

        return this.madrasaRepository.save(madrasa);
    }
    public List<Madrasa_build> getAllMadrasa(){

        return this.madrasaRepository.findAll();
    }
    public Optional<Madrasa_build> getMadrasaById(Integer id){

        return this.madrasaRepository.findById(id);
    }
    public Madrasa_build updateMadrasa(Madrasa_build madrasaBuild){
        Madrasa_build madrasaBuild1= this.madrasaRepository.findById(madrasaBuild.getId()).orElse(null);
        madrasaBuild1.setAssissthead_phone(madrasaBuild.getAssissthead_phone());
        madrasaBuild1.setAssisthead_firstName(madrasaBuild.getAssisthead_firstName());
        madrasaBuild1.setAssisthead_secName(madrasaBuild.getAssisthead_secName());
        madrasaBuild1.setAssisthead_lastName(madrasaBuild.getAssisthead_lastName());
        madrasaBuild1.setHead_firstName(madrasaBuild.getHead_firstName());
        madrasaBuild1.setHead_sectName(madrasaBuild.getHead_sectName());
        madrasaBuild1.setHead_lasttName(madrasaBuild.getHead_lasttName());
        madrasaBuild1.setHead_phone(madrasaBuild.getHead_phone());
        madrasaBuild1.setDistrict(madrasaBuild.getDistrict());
        madrasaBuild1.setImageData(madrasaBuild.getImageData());
        madrasaBuild1.setRegion(madrasaBuild.getRegion());
        madrasaBuild1.setDate(madrasaBuild.getDate());
        madrasaBuild1.setDiscription(madrasaBuild.getDiscription());
        madrasaBuild1.setFileData(madrasaBuild.getFileData());
        madrasaBuild1.setShehia(madrasaBuild.getShehia());

        madrasaBuild1.setStreet(madrasaBuild.getStreet());
        madrasaBuild1.setStatus(madrasaBuild.getStatus());

        return this.madrasaRepository.save(madrasaBuild1);
    }
    public void deleteMadrasa(Integer id){
        this.madrasaRepository.deleteById(id);
    }

}
