package com.si.uinam.absensi36restfull.services;

import com.si.uinam.absensi36restfull.models.CategoryModel;
import com.si.uinam.absensi36restfull.models.GroupModel;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPointInterface {

    @GET("/api/v3/statistik/harian-grup-grup")
    Call<ArrayList<GroupModel>> getGroupList(@Query("tgl") Date tgl, @Header("USER-ID") int userId, @Header("TOKEN") String apiKey);

    @GET("/api/v3/statistik/presensi-terbaik")
    Call<ArrayList<CategoryModel>> getBestList(@Query("tgl") Date tgl, @Header("USER-ID") int userId, @Header("TOKEN") String apiKey);

    @GET("/api/v3/statistik/presensi-terburuk")
    Call<ArrayList<CategoryModel>> getWorstList(@Query("tgl") Date tgl, @Header("USER-ID") int userId, @Header("TOKEN") String apiKey);

    //url= /3/movie/475557/credits?api_key=2f766223589e24c61b0aecdf89ec841d
    @GET("/3/{watchable}/{id}/credits")
    Call<GroupModel> getWatchableCredit(@Path("watchable") String watchable, @Path("id") int watchableId, @Query("api_key") String apiKey, @Query("language") String language);

    //url = /3/movie/47557/reviews?api_key=2f766223589e24c61b0aecdf89ec841d&language=en-US&page=1
    @GET("/3/{watchable}/{id}/reviews")
    Call<GroupModel> getWatchableReview(@Path("watchable") String watchable, @Path("id") int watchableId, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page);

}
