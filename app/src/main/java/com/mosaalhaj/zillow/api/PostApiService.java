package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.response.PagingResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PostApiService {

    @Headers("Content-type: application/json; charset=utf-8")
    @GET("api/RealEstates/GetAll/{page}/{pageSize}")
    Single<Response<MyRes<PagingResponse<ArrayList<Post>>>>> getAllPosts (
            @Path("page") int page,
            @Path("pageSize") int pageSize,
            @Header("Authorization") String token
            );

}
