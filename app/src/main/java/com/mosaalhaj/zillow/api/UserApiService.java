package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.model.User;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApiService {

    @Headers("Content-type: application/json; charset=utf-8")
    @POST("api/User/Create")
    Single<Response<MyRes<String>>> create (@Body User user);

}
