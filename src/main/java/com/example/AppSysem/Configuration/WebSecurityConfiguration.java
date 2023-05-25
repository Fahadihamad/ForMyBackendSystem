package com.example.AppSysem.Configuration;

import com.example.AppSysem.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    @Autowired
    public JwtService jwtService;
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(5242880); // Set the maximum file size limit
        return resolver;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{

        return  super.authenticationManagerBean();

    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors();
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate","/createStaffs","/createApplicant","/createSponsors","/currentuser","/api/masjid/add","/api/masjid/find/{id}","/api/masjid/all","/api/masjid/delete/{id}","/api/masjid/update/{id}","/alluser","/find/{id}","/delete/{id}","/allstaffs","/findstaff/{id}","/deletestaff/{id}","/updateStaff/{id}","/api/madrasa/delete/{id}","/api/madrasa/update/{id}","/api/madrasa/get/{id}","/api/madrasa/all","/api/madrasa/add","/api/madrasa/reject/{id}","/api/madrasa/accept/{id}","/api/madrasa/accept","/api/masjid/accept/{id}","/api/masjid/reject/{id}","/api/masjid/accept").permitAll()
                .antMatchers(HttpHeaders.ALLOW).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
     @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());

    }
}
