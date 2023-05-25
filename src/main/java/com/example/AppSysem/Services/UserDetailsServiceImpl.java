//package com.example.AppSysem.Services;
//
//import com.example.AppSysem.Entity.Users;
//import com.example.AppSysem.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//
//        Users users = this.userRepository.findByUserName(userName);
//
//        if (userName == null) {
//            System.out.println("user name not found");
//            throw new UsernameNotFoundException("no user with this cridentials");
//        }
//        return users;
//    }
//}
