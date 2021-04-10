package com.mosaalhaj.zillow.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("id")
    private int id;
    @SerializedName("imageUrl")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
