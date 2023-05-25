package com.example.AppSysem.Entity;

import lombok.Data;

import javax.persistence.Entity;
@Data
@Entity
public class Staffs extends Users{
     private String staffId;
}
