package com.mosaalhaj.zillow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.PostApiService;
import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.response.PagingResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.mosaalhaj.zillow.item.Constants.UN_AUTHORIZED;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<Response<PagingResponse<ArrayList<Post>>>> liveData ;
    private final PostApiService service;

    public HomeViewModel (){
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(PostApiService.class);
    }

    public void getPosts (int page,String token){

        // Get Data From Server With Retrofit

        final int pageSize = 10 ;

        Call<Response<PagingResponse<ArrayList<Post>>>> postsCall =
                service.getAllPosts(page,pageSize,token);

        postsCall.enqueue(new Callback<Response<PagingResponse<ArrayList<Post>>>>() {
            @Override
            public void onResponse(Call<Response<PagingResponse<ArrayList<Post>>>> call, retrofit2.Response<Response<PagingResponse<ArrayList<Post>>>> response) {

                Response<PagingResponse<ArrayList<Post>>> postsResponse;

                if (response.code() == 200 && response.body()!= null)
                    postsResponse =
                            new Response<>(true,response.body().getMessage()
                                    ,response.body().getData());

                else if (response.code() == 401){
                    postsResponse =
                            new Response<>(false,UN_AUTHORIZED
                                    ,null);
                }
                else
                    postsResponse =
                            new Response<>(false,"Error When Get Data"
                                    ,null);

                liveData.setValue(postsResponse);

            }

            @Override
            public void onFailure(Call<Response<PagingResponse<ArrayList<Post>>>> call, Throwable t) {

                Response<PagingResponse<ArrayList<Post>>> postsResponse =
                        new Response<>(false,"Can't Connect With Server"
                                ,null);

                liveData.setValue(postsResponse);

            }
        });


    }


}
