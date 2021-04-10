package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApiService {

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/User/Create")
    public Call<Response<String>> create (@Body User user);

}
