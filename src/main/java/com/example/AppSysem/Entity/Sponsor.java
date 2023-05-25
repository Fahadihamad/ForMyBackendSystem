package com.example.AppSysem.Entity;

import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class Sponsor extends Users{
    private String address;
}
