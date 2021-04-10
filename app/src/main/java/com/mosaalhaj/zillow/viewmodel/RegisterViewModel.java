package com.mosaalhaj.zillow.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.api.UserApiService;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RegisterViewModel extends ViewModel {

    public MutableLiveData<Response<String>> liveData;
    private final UserApiService service;

    public RegisterViewModel() {
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(UserApiService.class);
    }


    public void register(User user) {

        // Create New Account With Retrofit

        Call<Response<String>> createUserCall = service.create(user);

        createUserCall.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                if (response.isSuccessful() && response.body() != null)
                    liveData.setValue(response.body());
                else {
                    Response<String> createUserResponse =
                            new Response<String>(false, "Error When Create User", null);
                    liveData.setValue(createUserResponse);
                }

            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Response<String> createUserResponse =
                        new Response<>(false, "Can't Connect With Server", null);
                liveData.setValue(createUserResponse);
            }
        });

    }

}
