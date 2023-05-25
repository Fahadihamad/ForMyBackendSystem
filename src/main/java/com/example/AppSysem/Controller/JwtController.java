package com.example.AppSysem.Controller;

import com.example.AppSysem.Configuration.JwtRequestFilter;
import com.example.AppSysem.Entity.JwtRequest;
import com.example.AppSysem.Entity.JwtResponse;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Services.JwtService;
//import com.example.AppSysem.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController

@CrossOrigin("*")
public class JwtController {

    @Autowired
    private JwtService jwtService;
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest)throws Exception{

      return jwtService.createJwtToken(jwtRequest);

    }

//    @ResponseBody
//    @GetMapping("/currentuser")
//
//    public Users getCurrentUser( Principal principal){
//
//        return  ((Users) this.userDetailsService.loadUserByUsername(principal.getName()));
//
//    }


}
