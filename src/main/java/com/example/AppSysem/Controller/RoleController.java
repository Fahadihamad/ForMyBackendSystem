package com.example.AppSysem.Controller;

import com.example.AppSysem.Entity.Role;
import com.example.AppSysem.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    public RoleService roleService;

    @PostMapping({"/createRole"})
    public Role createRole(@RequestBody Role role){

        return roleService.createRole(role);

    }
}
