package com.si.uinam.absensi36restfull.services;

import com.si.uinam.absensi36restfull.helpers.ApiHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = ApiHelper.getBaseUrl();

    static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
