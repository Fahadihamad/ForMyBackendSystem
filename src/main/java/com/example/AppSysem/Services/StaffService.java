package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.StaffRepository;
import com.example.AppSysem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public StaffRepository staffRepository;

    public List<Staffs> getAllStaffs(){
        return (List<Staffs>) this.staffRepository.findAll();
    }
    public Staffs findAllStaffsdByUserId(Integer id){
        return (Staffs) this.staffRepository.findUserById(id)
                .orElseThrow(()-> new ResourceNotFoundException("that id"+id+"not found"));
    }
    public Staffs fetchStaffByUserName(String userName){
        return this.staffRepository.findStaffByUserName(userName);
    }
    @PreRemove
    public void deleteStaffs(Integer id){

        this.staffRepository.deleteUserById(id);
    }
    public Staffs updateStaff(Staffs staffs){
        Staffs staffs1 = (Staffs) userRepository.findById(staffs.getId()).orElse(null);
        staffs1.setStaffId(staffs.getStaffId());
        staffs1.setEmail(staffs.getEmail());
        staffs1.setPassword(staffs.getPassword()) ;
        staffs1.setPhone(staffs.getPhone());
        staffs1.setFirstName(staffs.getFirstName());
        staffs1.setLastName(staffs.getLastName());
        staffs1.setUserName(staffs.getUserName());
        return userRepository.save(staffs1);
    }
}

