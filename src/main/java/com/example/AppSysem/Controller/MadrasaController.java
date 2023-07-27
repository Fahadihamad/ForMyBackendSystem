package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Madrasa_build;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Repository.MadrasaRepository;

import com.example.AppSysem.Services.FileStorageService;
import com.example.AppSysem.Services.MadrasaBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/madrasa")
@CrossOrigin(origins = "http://localhost:4200")

public class MadrasaController {
    @Autowired
    public MadrasaRepository madrasaRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    public MadrasaBuildService madrasaBuildService;
    @PostMapping("/add")
    public ResponseEntity<Madrasa_build> createMadrasaBuild(@ModelAttribute Madrasa_build madrasaBuild,
                                                            @RequestParam("file") MultipartFile file,
                                                            @RequestParam("image") MultipartFile image) {
        try {
            // Set the file data in the MassjidBuild object
            if (!file.isEmpty()) {
                madrasaBuild.setFileData(file.getBytes());
                saveFileToDirectory(file);
            }

            // Set the image data in the MassjidBuild object
            if (!image.isEmpty()) {
                madrasaBuild.setImageData(image.getBytes());
                saveImageToDirectory(image);
            }
        } catch (IOException e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the MassjidBuild object in the database
        Madrasa_build savedMassjidBuild = madrasaRepository.save(madrasaBuild);

        return new ResponseEntity<>(savedMassjidBuild, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Madrasa_build> getMassjidBuildDetails(@PathVariable Integer id) {
        Optional<Madrasa_build> massjidBuildOptional = madrasaRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Madrasa_build madrasaBuild = massjidBuildOptional.get();
        String fileUrl = "/api/madrasa/file/" + madrasaBuild.getId();
        String imageUrl = "/api/madrasa/image/" + madrasaBuild.getId();
        madrasaBuild.setFileData(fileUrl.getBytes());
        madrasaBuild.setImageData(imageUrl.getBytes());

        return new ResponseEntity<>(madrasaBuild, HttpStatus.OK);
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Optional<Madrasa_build> massjidBuildOptional = madrasaRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Madrasa_build madrasaBuild = massjidBuildOptional.get();
        byte[] imageData = madrasaBuild.getImageData();
        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", madrasaBuild.getImageFileName());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Optional<Madrasa_build> massjidBuildOptional = madrasaRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Madrasa_build madrasaBuild = massjidBuildOptional.get();
        byte[] fileData = madrasaBuild.getFileData();
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", madrasaBuild.getFileFileName());

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
    public List<Madrasa_build> getAllMasjid(){
        return this.madrasaBuildService.getAllMadrasa();
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

    @GetMapping("/pending")
    public List<Madrasa_build> getPendingApplications() {

    return madrasaRepository.findByStatus("Pending");
    }
}