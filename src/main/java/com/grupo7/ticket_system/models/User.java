package com.grupo7.ticket_system.models;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
    
    int userId;
    String email;
    String documentCountry;
    String documentType;
    String documentNumber;
    String streetAddress;
    String numberAddress;
    String postalCode;
    int postalCodeId;
    ArrayList<String> phones;
    String username;
    String password;
    String rol= "general";
    
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPostalCodeId() {
        return postalCodeId;
    }
    public void setPostalCodeId(int postalCodeId) {
        this.postalCodeId = postalCodeId;
    }
    public ArrayList<String> getPhones() {
        return phones;
    }
    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDocumentCountry() {
        return documentCountry;
    }
    public void setDocumentCountry(String documentCountry) {
        this.documentCountry = documentCountry;
    }
    public String getDocumentType() {
        return documentType;
    }
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    public String getDocumentNumber() {
        return documentNumber;
    }
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    public String getNumberAddress() {
        return numberAddress;
    }
    public void setNumberAddress(String numberAddress) {
        this.numberAddress = numberAddress;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + ", documentCountry=" + documentCountry
                + ", documentType=" + documentType + ", documentNumber=" + documentNumber + ", streetAddress="
                + streetAddress + ", numberAddress=" + numberAddress + ", postalCode=" + postalCode + "]";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");   //IMPLEMENT rol

    }
}
