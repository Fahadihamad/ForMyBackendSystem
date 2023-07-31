package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Sponsor;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Services.JwtService;
import com.example.AppSysem.Services.SponsorService;
import com.example.AppSysem.Services.StaffService;
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
    @Autowired
    public StaffService staffService;
    @Autowired
    public SponsorService sponsorService;

    @PostConstruct
    public void initRolesAndUsers() {

        userServices.initRolesAndUser();
    }

    @PostMapping({"/createApplicant"})
    public Users createNewApplicant(@RequestBody Users applicant) throws Exception {

        String name = applicant.getUserName();
        if(name != null && !"".equals(name)){
            Users applicant1 =  userServices.fetchStaffByUserName(name);
            if(applicant1 !=null){
                throw new Exception("user with user name"+" "+name+" "+"already exist");
            }
        }
        Users applicant1= null;
        applicant1 = userServices.createNewApplicant(applicant);
        return applicant1;

    }

    @PostMapping({"/createStaffs"})
    public Staffs createNewStaffs(@RequestBody Staffs staffs) throws Exception {
         String name = staffs.getUserName();
         if(name != null && !"".equals(name)){
           Staffs staffs1 =  staffService.fetchStaffByUserName(name);
           if(staffs1 !=null){
               throw new Exception("user with user name"+" "+name+" "+"already exist");
           }
         }
        Staffs staffs1= null;
         staffs1 = userServices.createNewStaffs(staffs);
         return staffs1;
    }

    @PostMapping({"/createSponsors"})
    public Sponsor createNewSponsors(@RequestBody Sponsor sponsors) throws Exception {

        String name = sponsors.getUserName();
        if(name != null && !"".equals(name)){
            Sponsor sponsor1 =  sponsorService.fetchStaffByUserName(name);
            if(sponsor1 !=null){
                throw new Exception("user with user name"+" "+name+" "+"already exist");
            }
        }
        Sponsor sponsor1= null;
        sponsor1 = userServices.createNewSponsors(sponsors);
        return sponsor1;
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
