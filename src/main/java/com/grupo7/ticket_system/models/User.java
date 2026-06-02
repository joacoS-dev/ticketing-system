package com.grupo7.ticket_system.models;

public class User {
    
    int id_user;
    String email;
    String document_country;
    String document_type;
    String document_number;
    String street_address;
    String number_address;
    int postal_code;

    public int getId_user() {
        return id_user;
    }
    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDocument_country() {
        return document_country;
    }
    public void setDocument_country(String document_country) {
        this.document_country = document_country;
    }
    public String getDocument_type() {
        return document_type;
    }
    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }
    public String getDocument_number() {
        return document_number;
    }
    public void setDocument_number(String document_number) {
        this.document_number = document_number;
    }
    public String getStreet_address() {
        return street_address;
    }
    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }
    public String getNumber_address() {
        return number_address;
    }
    public void setNumber_address(String number_address) {
        this.number_address = number_address;
    }
    public int getPostal_code() {
        return postal_code;
    }
    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }
    @Override
    public String toString() {
        return "User [id_user=" + id_user + ", email=" + email + ", document_country=" + document_country
                + ", document_type=" + document_type + ", document_number=" + document_number + ", street_address="
                + street_address + ", number_address=" + number_address + ", postal_code=" + postal_code + "]";
    }
}
