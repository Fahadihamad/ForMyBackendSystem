package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Services.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin
public class ApplicationController {

    public ApplicationService applicationService;

    @PostMapping("/createApplication")
//    public Application createNewApplication(@RequestBody Application application) {
//
//        return applicationService.createNewApplication(application);
//    }


    @GetMapping("/getAllApplication")
    public List<Application> getAllAplications() {

        return applicationService.getApplication();
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
//        return ResponseEntity.ok("file uploaded succesfully");
//    }
@PostMapping("/upload")
public ResponseEntity<String> uploadFile(@RequestPart("letter") Part file) {
    if (file != null && file.getSize() > 0) {
        // Perform operations on the file
        return ResponseEntity.ok("File uploaded successfully");
    } else {
        return ResponseEntity.badRequest().body("No file uploaded");
    }
}
}