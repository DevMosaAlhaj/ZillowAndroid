package com.mosaalhaj.zillow.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("countryName")
    private String countryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.cityName);
        dest.writeString(this.countryName);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.cityName = source.readString();
        this.countryName = source.readString();
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.id = in.readInt();
        this.cityName = in.readString();
        this.countryName = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
