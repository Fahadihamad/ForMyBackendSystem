package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Entity.Personals;
import com.example.AppSysem.Repository.OrphansRepository;
import com.example.AppSysem.Repository.PrivateRepository;
import com.example.AppSysem.Services.PrivateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/private")
@CrossOrigin("*")
public class PrivateController {
    @Autowired
    private PrivateServices privateServices;
    @Autowired
    private PrivateRepository privateRepository;

    @PostMapping("/add")
    public Personals addPersonal(@RequestBody Personals personals){
        return this.privateServices.addPersonal(personals);
    }
    @GetMapping("/all")
    public List<Personals> getAllPersonal(){
        return this.privateServices.getAllPersonal();
    }
    @GetMapping("/get/{id}")
    public Personals getPersonalById(@PathVariable Integer id){
        return this.privateServices.findPersonalById(id);
    }
    @Transactional
    @PutMapping("/update/{id}")
    public Personals updatePersonal(@RequestBody Personals personals){
        return this.privateServices.updatePersonal(personals);
    }
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deletePersonal(@PathVariable Integer id){

        this.privateServices.deletePersonal(id);
    }

    @PutMapping("/accept/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> acceptApplication(@PathVariable("id") Integer id) {
        Optional<Personals> applicationOptional = privateRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Personals application = applicationOptional.get();
            application.setStatus("Accepted");
            privateRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reject/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") Integer id) {
        Optional<Personals> applicationOptional = privateRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Personals application = applicationOptional.get();
            application.setStatus("Rejected");
            privateRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accept")
//    @PreAuthorize("hasRole('Admin','Staff','Sponsors')")
    public List<Personals> getAcceptedApplications() {

        return privateRepository.findByStatus("Accepted");
    }
    @GetMapping("/reject")
    public List<Personals> getRejectedApplications() {
        return privateRepository.findByStatus("Rejected");
    }
}
