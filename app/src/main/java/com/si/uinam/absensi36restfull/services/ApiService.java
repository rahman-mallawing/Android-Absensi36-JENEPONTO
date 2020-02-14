package com.si.uinam.absensi36restfull.services;

import com.si.uinam.absensi36restfull.models.CategoryModel;
import com.si.uinam.absensi36restfull.models.GroupModel;
import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.models.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("/api/v3/auth/login")
    @FormUrlEncoded
    Call<UserModel> getUser(
            @Field("name") String username,
            @Field("password") String password);

    @GET("/api/v3/statistik/harian-grup-grup")
    Call<ArrayList<GroupModel>> getGroupList(
            @Query("tgl") String tgl);

    @GET("/api/v3/statistik/presensi-terbaik")
    Call<ArrayList<CategoryModel>> getBestList(
            @Query("tgl") String tgl);

    @GET("/api/v3/statistik/presensi-terburuk")
    Call<ArrayList<CategoryModel>> getWorstList(
            @Query("tgl") String tgl);

    @GET("/api/v3/statistik/harian")
    Call<StatistikModel> getStatistikHarian(
            @Query("tgl") String tgl);

    @GET("/api/v3/statistik/bulanan-tahun")
    Call<ArrayList<StaBulananTahunModel>> getStatistikBulananTahun(
            @Query("tgl") String tgl);
}
