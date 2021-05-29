package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.item.LoginDto;
import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.response.LoginResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApiService {

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/Auth/Login")
    Single<Response<MyRes<LoginResponse>>> login (@Body LoginDto dto);

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/Auth/Refresh")
    Single<Response<MyRes<LoginResponse>>> refresh (@Body String refreshToken);

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/Auth/RegisterFcm")
    Single<Response<MyRes<String>>> registerFcmToken (
            @Body String fcmToken,
            @Header("Authorization") String accessToken
    );

}
