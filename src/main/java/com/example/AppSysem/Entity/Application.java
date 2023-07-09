package com.example.AppSysem.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.servlet.http.Part;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status="Pending";

    private String date;
    private String discription;
    @Lob
    private byte[] fileData;
    private String district;
    private String region;
    private String shehia;
    private String imageFileName="imagefile";
    private String fileFileName="filename";

    private String street;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userId",insertable = false,updatable = false)
    private Users users;
    private String userName;
    @Lob
    private byte[] imageData;

//    public Application() {
//    }
//
//    public Application(Time time, String status, Date date, String discription, String district, String region, String shehia, String street, Users users, int userName, String letter) {
//        this.time = time;
//        this.status = status;
//        this.date = date;
//        this.discription = discription;
//
//        this.district = district;
//        this.region = region;
//        this.shehia = shehia;
//        this.street = street;
//        this.users = users;
//        this.userName = userName;
//
//    }
//
//    public Time getTime() {
//        return time;
//    }
//
//    public void setTime(Time time) {
//        this.time = time;
//    }
//
//    public String isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//
//        this.status = status;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public String getDiscription() {
//        return discription;
//    }
//
//    public void setDiscription(String discription) {
//        this.discription = discription;
//    }
//
//
//
//
//
//    public String getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(String district) {
//        this.district = district;
//    }
//
//    public String getRegion() {
//        return region;
//    }
//
//    public void setRegion(String region) {
//        this.region = region;
//    }
//
//    public String getShehia() {
//        return shehia;
//    }
//
//    public void setShehia(String shehia) {
//        this.shehia = shehia;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public Users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Users users) {
//        this.users = users;
//    }
//
//    public int getUserName() {
//        return userName;
//    }
//
//    public void setUserName(int userName) {
//        this.userName = userName;
//
//    }
}
