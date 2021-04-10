package com.mosaalhaj.zillow.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mosaalhaj.zillow.api.AuthApiService;
import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.item.LoginDto;
import com.mosaalhaj.zillow.model.Response;
import com.mosaalhaj.zillow.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<Response<LoginResponse>> liveData ;
    private final AuthApiService service ;

    public LoginViewModel (){
        liveData = new MutableLiveData<>();
        Retrofit retrofit = RetrofitSingleton.getInstance();
        service = retrofit.create(AuthApiService.class);
    }

    public void login (String email , String pass){

        //Login in Server with Retrofit

        LoginDto dto = new LoginDto();
        dto.setEmail(email);
        dto.setPassword(pass);

        Call<Response<LoginResponse>> loginCall = service.login(dto);

        loginCall.enqueue(new Callback<Response<LoginResponse>>() {
            @Override
            public void onResponse(Call<Response<LoginResponse>> call, retrofit2.Response<Response<LoginResponse>> response) {

                Response<LoginResponse> authResponse ;

                if (response.isSuccessful() && response.body()!= null){
                     authResponse =
                             new Response<>
                                     (true, "Login Successfully", response.body().getData());


                } else
                    authResponse = new Response<>(false,"Email or Password is incorrect",null);

                liveData.setValue(authResponse);
            }

            @Override
            public void onFailure(Call<Response<LoginResponse>> call, Throwable t) {

                Response<LoginResponse> authResponse = new Response<>(false,"Can't Connect With Server",null);

                liveData.setValue(authResponse);
                t.printStackTrace();

            }
        });


    }

    public void refresh (String refreshToken){

        Call<Response<LoginResponse>> refreshCall = service.refresh(refreshToken);

        refreshCall.enqueue(new Callback<Response<LoginResponse>>() {
            @Override
            public void onResponse(Call<Response<LoginResponse>> call, retrofit2.Response<Response<LoginResponse>> response) {
                Response<LoginResponse> refreshResponse ;

                if (response.isSuccessful() && response.body()!= null){
                    refreshResponse =
                            new Response<>
                                    (true, "Login Successfully", response.body().getData());


                } else
                    refreshResponse = new Response<>(false,"Email or Password is incorrect",null);

                liveData.setValue(refreshResponse);
            }

            @Override
            public void onFailure(Call<Response<LoginResponse>> call, Throwable t) {

                Response<LoginResponse> refreshResponse = new Response<>(false,"Can't Connect With Server",null);

                liveData.setValue(refreshResponse);
                t.printStackTrace();

            }
        });

    }

}
