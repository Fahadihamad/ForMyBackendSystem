package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Orphans;
import com.example.AppSysem.Entity.Personals;
import com.example.AppSysem.Repository.OrphansRepository;
import com.example.AppSysem.Repository.PrivateRepository;
import com.example.AppSysem.Services.PrivateServices;
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
@RequestMapping("/api/private")
@CrossOrigin("*")
public class PrivateController {
    @Autowired
    private PrivateServices privateServices;
    @Autowired
    private PrivateRepository privateRepository;
    @Value("${upload.directory}")
    private String uploadDirectory;
    @PostMapping("/add")
    public ResponseEntity<Personals> createMadrasaBuild(@ModelAttribute Personals personals,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("image") MultipartFile image) {
        try {
            // Set the file data in the MassjidBuild object
            if (!file.isEmpty()) {
                personals.setFileData(file.getBytes());
                saveFileToDirectory(file);
            }

            // Set the image data in the MassjidBuild object
            if (!image.isEmpty()) {
                personals.setImageData(image.getBytes());
                saveImageToDirectory(image);
            }
        } catch (IOException e) {
            // Handle the exception appropriately
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the MassjidBuild object in the database
        Personals savedMassjidBuild = privateRepository.save(personals);

        return new ResponseEntity<>(savedMassjidBuild, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Personals> getMassjidBuildDetails(@PathVariable Integer id) {
        Optional<Personals> massjidBuildOptional = privateRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Personals personals = massjidBuildOptional.get();
        String fileUrl = "/api/private/file/" + personals.getId();
        String imageUrl = "/api/private/image/" + personals.getId();
        personals.setFileData(fileUrl.getBytes());
        personals.setImageData(imageUrl.getBytes());

        return new ResponseEntity<>(personals, HttpStatus.OK);
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        Optional<Personals> massjidBuildOptional = privateRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Personals personals = massjidBuildOptional.get();
        byte[] imageData = personals.getImageData();
        if (imageData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", personals.getImageFileName());

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        Optional<Personals> massjidBuildOptional = privateRepository.findById(id);
        if (massjidBuildOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Personals personals = massjidBuildOptional.get();
        byte[] fileData = personals.getFileData();
        if (fileData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", personals.getFileFileName());

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
    public List<Personals> getAllPersonal(){
        return this.privateServices.getAllPersonal();
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

    @GetMapping("/pending")
    public List<Personals> getPendingApplications() {
        return privateRepository.findByStatus("Pending");
    }
}