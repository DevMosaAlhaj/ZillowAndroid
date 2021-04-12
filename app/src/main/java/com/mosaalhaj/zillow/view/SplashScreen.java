package com.mosaalhaj.zillow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.response.LoginResponse;
import com.mosaalhaj.zillow.service.MessagingService;
import com.mosaalhaj.zillow.viewmodel.LoginViewModel;

import static com.mosaalhaj.zillow.item.Constants.ACCESS_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.NOT_FOUND;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.REMEMBER_ME;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;
import static com.mosaalhaj.zillow.item.Constants.USER_EMAIL;
import static com.mosaalhaj.zillow.item.Constants.USER_ID;
import static com.mosaalhaj.zillow.item.Constants.USER_PASSWORD;

public class SplashScreen extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screeen);

        preferences = getSharedPreferences(SHARED_PREFERENCE_FILE,MODE_PRIVATE);

        if (preferences != null && preferences.getBoolean(REMEMBER_ME,false)){

            String refreshToken = preferences.getString(REFRESH_TOKEN,NOT_FOUND);

            if (!refreshToken.equals(NOT_FOUND)){
                LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

                viewModel.refresh(refreshToken);

                viewModel.liveData.observe(this, refreshResponse -> {
                    if (refreshResponse.isSucceeded()) {
                        storeUserData(refreshResponse.getData());
                        Intent intent = new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        toLoginActivity();
                });
            } else
                toLoginActivity();


        } else
            toLoginActivity();



        Intent intent = new Intent(getBaseContext(), MessagingService.class);
        startService(intent);

    }

    private void toLoginActivity() {
        new Handler().postDelayed(() -> {

            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();

        },3000);
    }

    @SuppressLint("ApplySharedPref")
    private void storeUserData (LoginResponse loginResponse){
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(REFRESH_TOKEN,loginResponse.getRefreshToken());
        editor.putString(ACCESS_TOKEN,loginResponse.getTokenResponse().getToken());

        editor.commit();
    }

}