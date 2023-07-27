package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Repository.OrphansRepository;
import com.example.AppSysem.Services.OrphansServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    @Value("${upload.directory}")
    private String uploadDirectory;

    @PostMapping("/add")
    public ResponseEntity<Orphans> createMadrasaBuild(@ModelAttribute Orphans orphans,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("image") MultipartFile image) {
        try {
            // Set the file data in the MassjidBuild object
            if (!file.isEmpty()) {
                orphans.setFileData(file.getBytes());
                saveFileToDirectory(file);
            }

            // Set the image data in the MassjidBuild object
            if (!image.isEmpty()) {
                orphans.setImageData(image.getBytes());
                saveImageToDirectory(image);
            }
        } catch (IOException e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the MassjidBuild object in the database
        Orphans savedMassjidBuild = orphansRepository.save(orphans);

        return new ResponseEntity<>(savedMassjidBuild, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Orphans> getMassjidBuildDetails(@PathVariable Integer id) {
        Optional<Orphans> massjidBuildOptional = orphansRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Orphans orphans = massjidBuildOptional.get();
        String fileUrl = "/api/orphan/file/" + orphans.getId();
        String imageUrl = "/api/orphan/image/" + orphans.getId();
        orphans.setFileData(fileUrl.getBytes());
        orphans.setImageData(imageUrl.getBytes());

        return new ResponseEntity<>(orphans, HttpStatus.OK);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Optional<Orphans> massjidBuildOptional = orphansRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Orphans orphans = massjidBuildOptional.get();
        byte[] imageData = orphans.getImageData();
        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", orphans.getImageFileName());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Optional<Orphans> massjidBuildOptional = orphansRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Orphans orphans = massjidBuildOptional.get();
        byte[] fileData = orphans.getFileData();
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", orphans.getFileFileName());

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }


    private void saveFileToDirectory(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = Path.of(uploadDirectory + File.separator + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void saveImageToDirectory(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            Path imagePath = Path.of(uploadDirectory + File.separator + imageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }


    @GetMapping("/all")
    public List<Orphans> getAllOrphans() {
        return this.orphansServices.getAllOrphans();
    }

    @Transactional
    @PutMapping("/update/{id}")
    public Orphans updateOrphans(@RequestBody Orphans orphans) {
        return this.orphansServices.updateOrphans(orphans);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteOrphans(@PathVariable Integer id) {

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

    @GetMapping("/pending")
    public List<Orphans> getPendingApplications() {
        return orphansRepository.findByStatus("Pending");
    }
}