package it.unisalento.bric48.backend.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("workers")
public class Worker {

    @Id
    String id;
    String rollNumber;
    String name;
    String surname;
    String email;
    String phoneNumber;
    String role;
    String idAdmin;
    String idHeadphones;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }
    public String getIdHeadphones() {
        return idHeadphones;
    }
    public void setIdHeadphones(String idHeadphones) {
        this.idHeadphones = idHeadphones;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    
}
