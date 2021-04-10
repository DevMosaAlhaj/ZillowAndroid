package com.mosaalhaj.zillow.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mosaalhaj.zillow.item.Constants.API_URL;
import static com.mosaalhaj.zillow.item.Settings.getOkHttpClient;

public abstract class RetrofitSingleton {

    private static Retrofit RETROFIT;

    private RetrofitSingleton (){}

    public static Retrofit getInstance (){
        if (RETROFIT == null)
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                    .client(getOkHttpClient())
                    .build();

        return RETROFIT;
    }


    public int getCount (){
        return 0;
    }


}
