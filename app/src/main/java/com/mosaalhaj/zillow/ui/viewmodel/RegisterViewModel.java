package com.mosaalhaj.zillow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.api.UserApiService;
import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.model.User;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterViewModel extends ViewModel {

    private final UserApiService service;
    public MutableLiveData<MyRes<String>> liveData;

    public RegisterViewModel() {
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(UserApiService.class);
    }


    public void register(User user) {

        // Create New Account With Retrofit

        Single<Response<MyRes<String>>> userObservable = service.create(user);


        //noinspection ResultOfMethodCallIgnored
        userObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful() && response.body() != null)
                        liveData.setValue(response.body());
                    else {
                        MyRes<String> createUserResponse =
                                new MyRes<>(false, "Error When Create User", null);
                        liveData.setValue(createUserResponse);
                    }
                }, error -> {
                    MyRes<String> createUserResponse =
                            new MyRes<>(false, "Can't Connect With Server", null);
                    liveData.setValue(createUserResponse);
                });


    }

}
