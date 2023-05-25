package com.example.AppSysem.Entity;

import javax.persistence.Entity;

@Entity
public class Personals extends Application{
    private String per_firstName;
    private String per_sectName;
    private String per_lasttName;
    private String per_phone;
    private String per_houseNo;

    public Personals() {
    }

    public Personals(String per_firstName, String per_sectName, String per_lasttName, String per_phone, String per_houseNo) {
        this.per_firstName = per_firstName;
        this.per_sectName = per_sectName;
        this.per_lasttName = per_lasttName;
        this.per_phone = per_phone;
        this.per_houseNo = per_houseNo;
    }

    public String getPer_firstName() {
        return per_firstName;
    }

    public void setPer_firstName(String per_firstName) {
        this.per_firstName = per_firstName;
    }

    public String getPer_sectName() {
        return per_sectName;
    }

    public void setPer_sectName(String per_sectName) {
        this.per_sectName = per_sectName;
    }

    public String getPer_lasttName() {
        return per_lasttName;
    }

    public void setPer_lasttName(String per_lasttName) {
        this.per_lasttName = per_lasttName;
    }

    public String getPer_phone() {
        return per_phone;
    }

    public void setPer_phone(String per_phone) {
        this.per_phone = per_phone;
    }

    public String getPer_houseNo() {
        return per_houseNo;
    }

    public void setPer_houseNo(String per_houseNo) {
        this.per_houseNo = per_houseNo;
    }
}
