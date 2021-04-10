package com.mosaalhaj.zillow.model;

import com.google.gson.annotations.SerializedName;

public class Address {

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
}
