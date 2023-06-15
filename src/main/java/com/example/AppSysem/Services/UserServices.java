package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Role;
import com.example.AppSysem.Entity.Sponsor;
import com.example.AppSysem.Entity.Staffs;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.RoleRepository;
import com.example.AppSysem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServices {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> getAllUsers(){
        return (List<Users>) this.userRepository.findAll();
    }
    public Users findAllUserdByUserName(Integer id){
        return this.userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("that id"+id+"not found"));
    }
    public void deleteUsers(Integer id){
        this.userRepository.deleteUserById(id);
    }

    public Users createNewApplicant(Users applicant){
        Role role = roleRepository.findById("Applicant").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        applicant.setRole(roles);
        applicant.setPassword(getEncodedPassword(applicant.getPassword()));
        return userRepository.save(applicant);
    }
    public Staffs createNewStaffs(Staffs staffs){
        Role role = roleRepository.findById("Staff").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        staffs.setRole(roles);
        staffs.setPassword(getEncodedPassword(staffs.getPassword()));
        return userRepository.save(staffs);
    }
    public Sponsor createNewSponsors(Sponsor sponsors){
        Role role = roleRepository.findById("Sponsors").get();

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        sponsors.setRole(roles);
        sponsors.setPassword(getEncodedPassword(sponsors.getPassword()));
        return userRepository.save(sponsors);
    }
//    public Users getUser(String username){
//
//        return this.userRepository.findByUsername(username);
//    }





    public void initRolesAndUser(){

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("admin role");
        roleRepository.save(adminRole);

        Role applicantRole = new Role();
        applicantRole.setRoleName("Applicant");
        applicantRole.setRoleDescription("Applicant role");
        roleRepository.save(applicantRole);

        Role staffRole = new Role();
        staffRole.setRoleName("Staff");
        staffRole.setRoleDescription("staff role");
        roleRepository.save(staffRole);

        Role sponsorRole = new Role();
        sponsorRole.setRoleName("Sponsors");
        sponsorRole.setRoleDescription("sponsor role");
        roleRepository.save(sponsorRole);

        Users adminUser = new Users();
        adminUser.setUserName("Admin45");
        adminUser.setFirstName("fahad");
        adminUser.setLastName("hamad");
        adminUser.setEmail("fahadpola222@gmail.com");
        adminUser.setPassword(getEncodedPassword("polar"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

//        Users applicantUser = new Users();
//        applicantUser.setUserName("Applicant");
//        applicantUser.setFirstName("Ali");
//        applicantUser.setLastName("hamad");
//        applicantUser.setEmail("alipola222@gmail.com");
//        applicantUser.setPassword(getEncodedPassword("polaman"));
//        Set<Role> applicantRoles = new HashSet<>();
//        applicantRoles.add(applicantRole);
//        applicantUser.setRole(applicantRoles);
//        userRepository.save(applicantUser);
//
//
//        Users staffUser = new Users();
//        staffUser.setUserName("Staff");
//        staffUser.setFirstName("fahad");
//        staffUser.setLastName("hamad");
//        staffUser.setEmail("fahadpola222@gmail.com");
//        staffUser.setPassword("pola");
//        Set<Role> staffRoles = new HashSet<>();
//        staffRoles.add(staffRole);
//        staffUser.setRole(staffRoles);
//        userRepository.save(staffUser);
//
//        Users sponsorUser = new Users();
//        sponsorUser.setUserName("Sponsor");
//        sponsorUser.setFirstName("fahad");
//        sponsorUser.setLastName("hamad");
//        sponsorUser.setEmail("fahadpola222@gmail.com");
//        sponsorUser.setPassword("pola");
//        Set<Role> sponsorRoles = new HashSet<>();
//        sponsorRoles.add(sponsorRole);
//        sponsorUser.setRole(sponsorRoles);
//        userRepository.save(sponsorUser);






    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }

}
