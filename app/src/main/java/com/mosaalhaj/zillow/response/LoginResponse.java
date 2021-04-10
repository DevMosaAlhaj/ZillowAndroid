package com.mosaalhaj.zillow.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("userId")
    private String userId ;
    @SerializedName("refreshToken")
    private String refreshToken ;
    @SerializedName("tokenViewModel")
    private AccessTokenResponse tokenResponse ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AccessTokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(AccessTokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }
}
