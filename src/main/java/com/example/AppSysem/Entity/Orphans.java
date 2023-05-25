package com.example.AppSysem.Entity;

import javax.persistence.Entity;

@Entity
public class Orphans extends Application{
    private String orp_firstName;
    private String orp_sectName;
    private String orp_lasttName;
    private String orp_phone;
    private String houseNo;


    public Orphans() {
    }


    public Orphans(String orp_firstName, String orp_sectName, String orp_lasttName, String orp_phone, String houseNo) {
        this.orp_firstName = orp_firstName;
        this.orp_sectName = orp_sectName;
        this.orp_lasttName = orp_lasttName;
        this.orp_phone = orp_phone;
        this.houseNo = houseNo;
    }

    public String getOrp_firstName() {
        return orp_firstName;
    }

    public void setOrp_firstName(String orp_firstName) {
        this.orp_firstName = orp_firstName;
    }

    public String getOrp_sectName() {
        return orp_sectName;
    }

    public void setOrp_sectName(String orp_sectName) {
        this.orp_sectName = orp_sectName;
    }

    public String getOrp_lasttName() {
        return orp_lasttName;
    }

    public void setOrp_lasttName(String orp_lasttName) {
        this.orp_lasttName = orp_lasttName;
    }

    public String getOrp_phone() {
        return orp_phone;
    }

    public void setOrp_phone(String orp_phone) {
        this.orp_phone = orp_phone;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
}
