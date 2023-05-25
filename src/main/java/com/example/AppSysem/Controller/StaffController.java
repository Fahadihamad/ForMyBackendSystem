package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PreRemove;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping
public class StaffController {
    @Autowired
 public StaffService staffService;

    @GetMapping("/allstaffs")
    public ResponseEntity<List<Staffs>> getAllStaffs() {
        List<Staffs> staffs = staffService.getAllStaffs();
        return new ResponseEntity<>(staffs, HttpStatus.OK);
    }
    @GetMapping("/findstaff/{id}")
    public ResponseEntity <Staffs> getAllStaffsByUsername(@PathVariable("id") Integer id){
        Staffs staffs = staffService.findAllStaffsdByUserId(id);
        return new  ResponseEntity<>(staffs, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deletestaff/{id}")
    public void deleteStaff(@PathVariable Integer id){
        this.staffService.deleteStaffs(id);
    }
    @PutMapping("/updateStaff/{id}")
    public Staffs updateStaffs(@RequestBody Staffs staffs){
        return this.staffService.updateStaff(staffs);
    }

}
