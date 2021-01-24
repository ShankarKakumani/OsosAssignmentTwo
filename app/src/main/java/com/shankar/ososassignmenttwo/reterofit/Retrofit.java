package com.shankar.ososassignmenttwo.reterofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static Retrofit instance = null;
    private final ApiService myApiService;

    private Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder().baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApiService = retrofit.create(ApiService.class);
    }

    public static synchronized Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit();
        }
        return instance;
    }

    public ApiService getMyApiService() {
        return myApiService;
    }
}

