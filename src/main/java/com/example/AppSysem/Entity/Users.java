package com.example.AppSysem.Entity;


import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
@Data
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    private long phone;

    @OneToMany(mappedBy = "users",fetch = FetchType.EAGER, cascade = {  CascadeType.ALL})

    private List<Application> applications;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
    joinColumns ={
            @JoinColumn(name = "userId")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "role_Id")
            }
    )
    private Set<Role> role;

    public Users() {
    }

    public Users(String userName, String firstName, String lastName, String password, String email, long phone, List<Application> applications, Set<Role> role) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.applications = applications;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
