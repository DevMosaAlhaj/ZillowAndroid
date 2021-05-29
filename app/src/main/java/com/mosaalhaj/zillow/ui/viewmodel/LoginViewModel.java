package com.mosaalhaj.zillow.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.AuthApiService;
import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.item.LoginDto;
import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.response.LoginResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {

    private final AuthApiService service;
    public MutableLiveData<MyRes<LoginResponse>> liveData;

    public LoginViewModel() {
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(AuthApiService.class);
    }

    public void login(String email, String pass) {

        //Login in Server with Retrofit

        LoginDto dto = new LoginDto();
        dto.setEmail(email);
        dto.setPassword(pass);

        Single<Response<MyRes<LoginResponse>>> loginObservable = service.login(dto);

        //noinspection ResultOfMethodCallIgnored
        loginObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    MyRes<LoginResponse> authResponse;

                    if (response.isSuccessful() && response.body() != null) {
                        authResponse =
                                new MyRes<>
                                        (true, "Login Successfully", response.body().getData());


                    } else
                        authResponse = new MyRes<>(false, "Email or Password is incorrect", null);

                    liveData.setValue(authResponse);
                }, error -> {
                    MyRes<LoginResponse> authResponse = new MyRes<>(false, "Can't Connect With Server", null);

                    liveData.setValue(authResponse);
                    error.printStackTrace();
                });


    }

    public void refresh(String refreshToken) {

        Single<Response<MyRes<LoginResponse>>> refreshObservable = service.refresh(refreshToken);

        //noinspection ResultOfMethodCallIgnored
        refreshObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    MyRes<LoginResponse> refreshResponse;

                    if (response.isSuccessful() && response.body() != null) {
                        refreshResponse =
                                new MyRes<>
                        (true, "Login Successfully", response.body().getData());


                    } else
                        refreshResponse = new MyRes<>(false, "Refresh Token is incorrect", null);

                    liveData.setValue(refreshResponse);
                }, error -> {
                    MyRes<LoginResponse> refreshResponse = new MyRes<>(false, "Can't Connect With Server", null);

                    liveData.setValue(refreshResponse);
                    error.printStackTrace();
                });



    }

}
