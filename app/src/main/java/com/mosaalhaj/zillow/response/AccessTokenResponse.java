package com.mosaalhaj.zillow.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AccessTokenResponse {

    @SerializedName("token")
    private String token;
    @SerializedName("expireAt")
    private Date expireAt;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
