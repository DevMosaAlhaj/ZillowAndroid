package com.mosaalhaj.zillow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.id);
    }

    public void readFromParcel(Parcel source) {
        this.firstName = source.readString();
        this.lastName = source.readString();
        this.phoneNumber = source.readString();
        this.email = source.readString();
        this.password = source.readString();
        this.id = source.readString();
    }

    public User() {
    }

    protected User(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNumber = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
