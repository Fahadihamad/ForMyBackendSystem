package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Repository.MadrasaRepository;

import com.example.AppSysem.Services.FileStorageService;
import com.example.AppSysem.Services.MadrasaBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/madrasa")
@CrossOrigin(origins = "http://localhost:4200")

public class MadrasaController {
    @Autowired
    public MadrasaRepository madrasaRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    public MadrasaBuildService madrasaBuildService;
    @PostMapping("/add")
    public Madrasa_build addMadrasa(@RequestBody Madrasa_build madrasaBuild){
        return this.madrasaBuildService.addMadrasa(madrasaBuild);
    }
    @GetMapping("/all")
    public List<Madrasa_build> getAllMasjid(){
        return this.madrasaBuildService.getAllMadrasa();
    }
    @GetMapping("/get/{id}")
    public Optional<Madrasa_build> getMadrasaById(@PathVariable Integer id){
        return this.madrasaBuildService.getMadrasaById(id);
    }
    @Transactional
    @PutMapping("/update/{id}")
    public Madrasa_build updateMadrasa(@RequestBody Madrasa_build madrasaBuild){
        return this.madrasaBuildService.updateMadrasa(madrasaBuild);
    }
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteMasjid(@PathVariable Integer id){

        this.madrasaBuildService.deleteMadrasa(id);
    }

    @PutMapping("/accept/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> acceptApplication(@PathVariable("id") Integer id) {
        Optional<Madrasa_build> applicationOptional = madrasaRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Madrasa_build application = applicationOptional.get();
            application.setStatus("Accepted");
            madrasaRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/reject/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") Integer id) {
        Optional<Madrasa_build> applicationOptional = madrasaRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Madrasa_build application = applicationOptional.get();
            application.setStatus("Rejected");
            madrasaRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accept")
//    @PreAuthorize("hasRole('Admin','Staff','Sponsors')")
    public List<Madrasa_build> getAcceptedApplications() {
        return madrasaRepository.findByStatus("Accepted");
    }
    @GetMapping("/reject")
    public List<Madrasa_build> getRejectedApplications() {
        return madrasaRepository.findByStatus("Rejected");
    }
    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        String letter = fileStorageService.storeFile(file);

        // Save the data entity with the file name and other attributes
        Madrasa_build data = new Madrasa_build(name, letter);
        // Save the data entity to the database or perform any other required operations
    }
}
