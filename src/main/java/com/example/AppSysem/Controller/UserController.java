package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Sponsor;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Services.JwtService;
import com.example.AppSysem.Services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;

@RestController()
@CrossOrigin("*")
public class UserController {
    @Autowired
    public UserServices userServices;
    @Autowired
    public JwtService jwtService;

    @PostConstruct
    public void initRolesAndUsers() {

        userServices.initRolesAndUser();
    }

    @PostMapping({"/createApplicant"})
    public Users createNewApplicant(@RequestBody Users applicant) {

        return userServices.createNewApplicant(applicant);

    }

    @PostMapping({"/createStaffs"})
    public Staffs createNewStaffs(@RequestBody Staffs staffs) {

        return userServices.createNewStaffs(staffs);
    }

    @PostMapping({"/createSponsors"})
    public Sponsor createNewSponsors(@RequestBody Sponsor sponsors) {

        return userServices.createNewSponsors(sponsors);
    }


//    @GetMapping("/username")
//    public Users getUser(@PathVariable("username") String username){
//
//        return this.userServices.getUser(username);
//    }
////

    @GetMapping("/alluser")
    public ResponseEntity<List<Users>> getAllMasjid(){
        List<Users> user = userServices.getAllUsers();
        return new  ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity <Users> getAllMasjidById(@PathVariable("userName") Integer id){
        Users users = userServices.findAllUserdByUserName(id);
        return new  ResponseEntity<>(users, HttpStatus.OK);
    }
    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteMasjid(@PathVariable Integer id){
        this.userServices.deleteUsers(id);
    }

}
