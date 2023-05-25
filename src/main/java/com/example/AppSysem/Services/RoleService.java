package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Role;
import com.example.AppSysem.Repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class RoleService {
    @Autowired
    public RoleRepository roleRepository;

    public Role createRole(Role role){

        return roleRepository.save(role);
    }
}
