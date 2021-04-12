package com.mosaalhaj.zillow.api;

import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.response.PagingResponse;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PostApiService {

    @Headers("Content-type: application/json; charset=utf-8")
    @GET("api/RealEstates/GetAll/{page}/{pageSize}")
    Call<Response<PagingResponse<ArrayList<Post>>>> getAllPosts (
            @Path("page") int page,
            @Path("pageSize") int pageSize,
            @Header("Authorization") String token
            );

}
