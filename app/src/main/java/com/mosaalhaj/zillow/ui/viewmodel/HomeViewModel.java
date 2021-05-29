package com.mosaalhaj.zillow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.PostApiService;
import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.model.Post;
import com.mosaalhaj.zillow.response.PagingResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.zillow.item.Constants.UN_AUTHORIZED;

public class HomeViewModel extends ViewModel {

    public MutableLiveData<MyRes<PagingResponse<ArrayList<Post>>>> liveData ;
    private final PostApiService service;

    public HomeViewModel (){
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(PostApiService.class);
    }

    public void getPosts (int page,String token){

        // Get Data From Server With Retrofit

        final int pageSize = 10 ;

        Single<Response<MyRes<PagingResponse<ArrayList<Post>>>>> postsObservable =
                service.getAllPosts(page,pageSize,token);

        //noinspection ResultOfMethodCallIgnored
        postsObservable.subscribeOn(Schedulers.io())
       .observeOn(AndroidSchedulers.mainThread())
       .subscribe(response->{
           MyRes<PagingResponse<ArrayList<Post>>> postsResponse;

           if (response.code() == 200 && response.body()!= null)
               postsResponse =
                       new MyRes<>(true,response.body().getMessage()
                               ,response.body().getData());

           else if (response.code() == 401){
               postsResponse =
                       new MyRes<>(false,UN_AUTHORIZED
                               ,null);
           }
           else
               postsResponse =
                       new MyRes<>(false,"Error When Get Data"
                               ,null);

           liveData.setValue(postsResponse);
       },error->{
           MyRes<PagingResponse<ArrayList<Post>>> postsResponse =
                   new MyRes<>(false,"Can't Connect With Server"
                           ,null);

           liveData.setValue(postsResponse);
       });


    }


}
