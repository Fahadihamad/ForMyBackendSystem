package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Repository.ApplicationRepository;
import com.example.AppSysem.Repository.MasijidRepository;
import com.example.AppSysem.Services.MasjidBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/masjid")
public class MasjidController {

    @Autowired
    public MasjidBuildService masjidBuildService;
    @Autowired
    public MasijidRepository masijidRepository;

    @PostMapping("/add")
    public Massjid_build addMasjid(@RequestBody Massjid_build massjid_build) {
        return this.masjidBuildService.addMasjid(massjid_build);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Massjid_build>> getAllMasjid() {
        List<Massjid_build> masjid = masjidBuildService.getAllMasjid();
        return new ResponseEntity<>(masjid, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Massjid_build> getAllMasjidById(@PathVariable("id") Integer id) {
        Massjid_build masjid = masjidBuildService.findMasjidById(id);
        return new ResponseEntity<>(masjid, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public Massjid_build updateMasjid(@RequestBody Massjid_build massjid_build) {
        return this.masjidBuildService.updateMasjid(massjid_build);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteMasjid(@PathVariable Integer id) {
        this.masjidBuildService.deleteMasjid(id);
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@ModelAttribute Massjid_build massjid_build) {
//        MultipartFile image = massjid_build.getImage();
//        MultipartFile letter = massjid_build.getLetter();
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//            File destFile = new File("path/to/save/uploads/" + fileName);
//            file.transferTo(destFile);
//            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.OK);
//        } catch (IOException ex) {
//            return  ResponseEntity.ok("file uploaded succesfully");
//        }
//    public ResponseEntity<String> uploadFile(@RequestParam("file") Part file) {
//        if (file != null) {
//            // Perform operations on the file
//            return ResponseEntity.ok("File uploaded successfully");
//        } else {
//            return ResponseEntity.badRequest().body("No file uploaded");
//        }
//    }
//    public ResponseEntity<String> uploadFile(@RequestPart("letter") Part file) {
//        if (file != null && file.getSize() > 0) {
//            // Perform operations on the file
//            return ResponseEntity.ok("File uploaded successfully");
//        } else {
//            return ResponseEntity.badRequest().body("No file uploaded");
//        }
//    }
@PutMapping("/accept/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
public ResponseEntity<?> acceptApplication(@PathVariable("id") Integer id) {
    Optional<Massjid_build> applicationOptional = masijidRepository.findById(id);
    if (applicationOptional.isPresent()) {
        Massjid_build application = applicationOptional.get();
        application.setStatus("Accepted");
        masijidRepository.save(application);
        return ResponseEntity.ok().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @PutMapping("/reject/{id}")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public ResponseEntity<?> rejectApplication(@PathVariable("id") Integer id) {
        Optional<Massjid_build> applicationOptional = masijidRepository.findById(id);
        if (applicationOptional.isPresent()) {
            Massjid_build application = applicationOptional.get();
            application.setStatus("Rejected");
            masijidRepository.save(application);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/accept")
//    @PreAuthorize("hasRole('Admin','Staff','Sponsors','Applicant')")
    public List<Massjid_build> getAcceptedApplications() {

        return masijidRepository.findByStatus("Accepted");
    }
    @GetMapping("/reject")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public List<Massjid_build> getRejectedApplications() {

        return masijidRepository.findByStatus("Rejected");
    }
}



