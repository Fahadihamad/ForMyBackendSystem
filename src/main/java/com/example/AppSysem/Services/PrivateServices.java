package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Entity.Personals;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.ApplicationRepository;
import com.example.AppSysem.Repository.PrivateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateServices {
    @Autowired
    private PrivateRepository privateRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public Personals addPersonal(Personals personals){

        return this.privateRepository.save(personals);
    }

    public List<Personals> getAllPersonal(){

        return this.privateRepository.findAll();
    }


    public Personals findPersonalById(Integer id){
        return this.applicationRepository.getPersonalById(id)
                .orElseThrow(()-> new ResourceNotFoundException("that id"+id+"not found"));
    }

    public void deletePersonal(Integer id){

        this.applicationRepository.deletePersonalById(id);
    }
    public Personals updatePersonal(Personals personals){
        Personals personals1 = applicationRepository.getPersonalById(personals.getId()).orElse(null);
        personals1.setDate(personals.getDate());
        personals1.setDistrict(personals.getDistrict());
        personals1.setLetter(personals.getLetter());
        personals1.setImage(personals.getImage());
        personals1.setRegion(personals.getRegion());
        personals1.setShehia(personals.getShehia());
        personals1.setStreet(personals.getStreet());
        personals1.setDistrict(personals.getDistrict());
        personals1.setStatus(personals.getStatus());
        personals1.setPer_firstName(personals.getPer_firstName());
        personals1.setPer_houseNo(personals.getPer_houseNo());
        personals1.setPer_lasttName(personals.getPer_lasttName());
        personals1.setPer_sectName(personals.getPer_sectName());
        personals1.setPer_phone(personals.getPer_phone());
        return applicationRepository.save(personals1);
    }
}
