package com.example.AppSysem.Entity;

import javax.persistence.Entity;

@Entity
public class Massjid_build extends Application {
    private String imam_firstName;
    private String Imam_sectName;
    private String imam_lasttName;
    private String imam_phone;
    private String assist_firstName;
    private String assist_secName;
    private String assist_lastName;
    private String assisst_phone;

    public Massjid_build() {
    }

    public Massjid_build(String imam_firstName, String imam_sectName, String imam_lasttName, String imam_phone, String assist_firstName, String assist_secName, String assist_lastName, String assisst_phone) {
        this.imam_firstName = imam_firstName;
        Imam_sectName = imam_sectName;
        this.imam_lasttName = imam_lasttName;
        this.imam_phone = imam_phone;
        this.assist_firstName = assist_firstName;
        this.assist_secName = assist_secName;
        this.assist_lastName = assist_lastName;
        this.assisst_phone = assisst_phone;
    }

    public String getImam_firstName() {
        return imam_firstName;
    }

    public void setImam_firstName(String imam_firstName) {
        this.imam_firstName = imam_firstName;
    }

    public String getImam_sectName() {
        return Imam_sectName;
    }

    public void setImam_sectName(String imam_sectName) {
        Imam_sectName = imam_sectName;
    }

    public String getImam_lasttName() {
        return imam_lasttName;
    }

    public void setImam_lasttName(String imam_lasttName) {
        this.imam_lasttName = imam_lasttName;
    }

    public String getImam_phone() {
        return imam_phone;
    }

    public void setImam_phone(String imam_phone) {
        this.imam_phone = imam_phone;
    }

    public String getAssist_firstName() {
        return assist_firstName;
    }

    public void setAssist_firstName(String assist_firstName) {
        this.assist_firstName = assist_firstName;
    }

    public String getAssist_secName() {
        return assist_secName;
    }

    public void setAssist_secName(String assist_secName) {
        this.assist_secName = assist_secName;
    }

    public String getAssist_lastName() {
        return assist_lastName;
    }

    public void setAssist_lastName(String assist_lastName) {
        this.assist_lastName = assist_lastName;
    }

    public String getAssisst_phone() {
        return assisst_phone;
    }

    public void setAssisst_phone(String assisst_phone) {
        this.assisst_phone = assisst_phone;
    }
}
