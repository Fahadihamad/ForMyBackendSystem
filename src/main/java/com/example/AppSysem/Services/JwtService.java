package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.JwtRequest;
import com.example.AppSysem.Entity.JwtResponse;
import com.example.AppSysem.Entity.Users;
import com.example.AppSysem.Repository.UserRepository;
import com.example.AppSysem.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest)throws Exception{
        String userName = jwtRequest.getUserName();
        String password = jwtRequest.getPassword();
        authenticate(userName,password);
        final UserDetails userDetails = loadUserByUsername(userName);
        String newGenerateToken = jwtUtils.generateToken(userDetails);
        Users users = userRepository.findByUserName(userName).get();

        return new JwtResponse(users,newGenerateToken);

    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = userRepository.findByUserName(userName).get();
        if (users != null){
         return new User(
                 users.getUserName(),
                 users.getPassword(),
                 getAuthorities(users)
         );

        }else {
            throw new UsernameNotFoundException("Username is not valid");

        }
    }
     private Set getAuthorities(Users users){
        Set authorities = new HashSet();
        users.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });

        return authorities;

     }
    public void authenticate(String userName, String password)throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName,password));

        }catch (DisabledException e){
            throw new Exception("User is disabled"+e.getMessage());
        }catch (BadCredentialsException e){
            throw new Exception("Bad credentials from user"+ e.getMessage());
        }

    }
}
