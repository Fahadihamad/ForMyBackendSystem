package com.example.AppSysem.Entity;

import javax.persistence.Entity;

@Entity
public class Madrasa_build extends Application{

    private String head_firstName;
    private String head_sectName;
    private String head_lasttName;
    private String head_phone;
    private String assisthead_firstName;
    private String assisthead_secName;
    private String assisthead_lastName;
    private String assissthead_phone;

    public Madrasa_build() {


    }

    public Madrasa_build(String head_firstName, String head_sectName, String head_lasttName, String head_phone, String assisthead_firstName, String assisthead_secName, String assisthead_lastName, String assissthead_phone) {
        this.head_firstName = head_firstName;
        this.head_sectName = head_sectName;
        this.head_lasttName = head_lasttName;
        this.head_phone = head_phone;
        this.assisthead_firstName = assisthead_firstName;
        this.assisthead_secName = assisthead_secName;
        this.assisthead_lastName = assisthead_lastName;
        this.assissthead_phone = assissthead_phone;
    }

    public String getHead_firstName() {
        return head_firstName;
    }

    public void setHead_firstName(String head_firstName) {
        this.head_firstName = head_firstName;
    }

    public String getHead_sectName() {
        return head_sectName;
    }

    public void setHead_sectName(String head_sectName) {
        this.head_sectName = head_sectName;
    }

    public String getHead_lasttName() {
        return head_lasttName;
    }

    public void setHead_lasttName(String head_lasttName) {
        this.head_lasttName = head_lasttName;
    }

    public String getHead_phone() {
        return head_phone;
    }

    public void setHead_phone(String head_phone) {
        this.head_phone = head_phone;
    }

    public String getAssisthead_firstName() {
        return assisthead_firstName;
    }

    public void setAssisthead_firstName(String assisthead_firstName) {
        this.assisthead_firstName = assisthead_firstName;
    }

    public String getAssisthead_secName() {
        return assisthead_secName;
    }

    public void setAssisthead_secName(String assisthead_secName) {
        this.assisthead_secName = assisthead_secName;
    }

    public String getAssisthead_lastName() {
        return assisthead_lastName;
    }

    public void setAssisthead_lastName(String assisthead_lastName) {
        this.assisthead_lastName = assisthead_lastName;
    }

    public String getAssissthead_phone() {
        return assissthead_phone;
    }

    public void setAssissthead_phone(String assissthead_phone) {
        this.assissthead_phone = assissthead_phone;
    }
}
