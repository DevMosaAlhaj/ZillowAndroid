package com.mosaalhaj.zillow.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("firstName")
    private String firstName ;
    @SerializedName("lastName")
    private String lastName ;
    @SerializedName("phoneNumber")
    private String phoneNumber ;
    @SerializedName("email")
    private String email ;
    @SerializedName("password")
    private String password ;
    @SerializedName("id")
    private String id ;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
