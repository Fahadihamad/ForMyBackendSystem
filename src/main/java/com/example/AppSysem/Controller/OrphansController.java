package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Repository.OrphansRepository;
import com.example.AppSysem.Services.OrphansServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orphan")
@CrossOrigin("*")
public class OrphansController {
    @Autowired
    private OrphansServices orphansServices;
    @Autowired
    private OrphansRepository orphansRepository;

    @PostMapping("/add")
    public Orphans addOrphans(@RequestBody Orphans orphans){
        return this.orphansServices.addOrphans(orphans);
    }
    @GetMapping("/all")
    public List<Orphans> getAllOrphans(){
        return this.orphansServices.getAllOrphans();
    }
    @GetMapping("/get/{id}")
    public Orphans getOrphansById(@PathVariable Integer id){
        return this.orphansServices.findOrphansById(id);
    }
    @Transactional
    @PutMapping("/update/{id}")
    public Orphans updateOrphans(@RequestBody Orphans orphans){
        return this.orphansServices.updateOrphans(orphans);
    }
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteOrphans(@PathVariable Integer id){

        this.orphansServices.deleteOrphans(id);
    }

    @PutMapping("/accept/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> acceptApplication(@PathVariable("id") Integer id) {
        Optional<Orphans> applicationOptional = orphansRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Orphans application = applicationOptional.get();
            application.setStatus("Accepted");
            orphansRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reject/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") Integer id) {
        Optional<Orphans> applicationOptional = orphansRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Orphans application = applicationOptional.get();
            application.setStatus("Rejected");
            orphansRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accept")
//    @PreAuthorize("hasRole('Admin','Staff','Sponsors')")
    public List<Orphans> getAcceptedApplications() {

        return orphansRepository.findByStatus("Accepted");
    }
    @GetMapping("/reject")
    public List<Orphans> getRejectedApplications() {
        return orphansRepository.findByStatus("Rejected");
    }
}
