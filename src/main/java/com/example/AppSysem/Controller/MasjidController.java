package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.ApplicationRepository;
import com.example.AppSysem.Repository.MasijidRepository;
import com.example.AppSysem.Services.MasjidBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/masjid")
public class MasjidController {
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Autowired
    public MasjidBuildService masjidBuildService;
    @Autowired
    private MasijidRepository masijidRepository;


    @PostMapping("/add")
    public ResponseEntity<Massjid_build> createMassjidBuild(@ModelAttribute Massjid_build massjidBuild,
                                                           @RequestParam("file") MultipartFile file,
                                                           @RequestParam("image") MultipartFile image) {
        try {
            // Set the file data in the MassjidBuild object
            if (!file.isEmpty()) {
                massjidBuild.setFileData(file.getBytes());
                saveFileToDirectory(file);
            }

            // Set the image data in the MassjidBuild object
            if (!image.isEmpty()) {
                massjidBuild.setImageData(image.getBytes());
                saveImageToDirectory(image);
            }
        } catch (IOException e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the MassjidBuild object in the database
        Massjid_build savedMassjidBuild = masijidRepository.save(massjidBuild);

        return new ResponseEntity<>(savedMassjidBuild, HttpStatus.CREATED);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Massjid_build> getMassjidBuildDetails(@PathVariable Integer id) {
        Optional<Massjid_build> massjidBuildOptional = masijidRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Massjid_build massjidBuild = massjidBuildOptional.get();
        String fileUrl = "/api/masjid/file/" + massjidBuild.getId();
        String imageUrl = "/api/masjid/image/" + massjidBuild.getId();
        massjidBuild.setFileData(fileUrl.getBytes());
        massjidBuild.setImageData(imageUrl.getBytes());

        return new ResponseEntity<>(massjidBuild, HttpStatus.OK);
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Optional<Massjid_build> massjidBuildOptional = masijidRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Massjid_build massjidBuild = massjidBuildOptional.get();
        byte[] imageData = massjidBuild.getImageData();
        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", massjidBuild.getImageFileName());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Optional<Massjid_build> massjidBuildOptional = masijidRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Massjid_build massjidBuild = massjidBuildOptional.get();
        byte[] fileData = massjidBuild.getFileData();
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", massjidBuild.getFileFileName());

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
    public ResponseEntity<List<Massjid_build>> getAllMasjid() {
        List<Massjid_build> masjid = masjidBuildService.getAllMasjid();
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
    @GetMapping("/pending")
//    @PreAuthorize("hasRole('Admin','Staff')")
    public List<Massjid_build> getPendingApplications() {

        return masijidRepository.findByStatus("Pending");
    }
}



