package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.item.LoginDto;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApiService {

    // @Header("Authorization")

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/Auth/Login")
    Call<Response<LoginResponse>> login (@Body LoginDto dto);

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/Auth/Refresh")
    Call<Response<LoginResponse>> refresh (@Body String refreshToken);
}
