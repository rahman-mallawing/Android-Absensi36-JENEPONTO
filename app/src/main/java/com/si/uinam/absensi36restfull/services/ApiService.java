package com.si.uinam.absensi36restfull.services;

import com.si.uinam.absensi36restfull.models.CategoryModel;
import com.si.uinam.absensi36restfull.models.GroupModel;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.MonthPresenceModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StaHarianBulanModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.models.UserModel;
import com.si.uinam.absensi36restfull.views.checklogpage.model.ChecklogModel;
import com.si.uinam.absensi36restfull.views.monthpresence.PresenceRequestForm;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @GET("/api/v3/statistik/hari-hari-bulan")
    Call<ArrayList<StaHarianBulanModel>> getStatistikHarianBulan(
            @Query("tgl") String tgl);

    @GET("/api/v3/presensi/kelola/laporan-harian-grup")
    Call<ArrayList<HarianGroupModel>> getLaporanHarianGrup(
            @Query("tgl") String tgl, @Query("grup_id") int grup_id);

    @GET("/api/v3/presensi/kelola/laporan-harian-absen")
    Call<ArrayList<HarianGroupModel>> getLaporanHarianAbsen(
            @Query("tgl") String tgl, @Query("sts_kehadiran") int sts_kehadiran);

    @GET("/api/v3/identitas/search")
    Call<PaginationModel<IdentityModel>> searchIdentity(
            @Query("query") String query,
            @Query("nama") String nama,
            @Query("nap") String nap,
            @Query("page") int page);

    @Headers("Content-Type: application/json")
    @POST("/api/v3/presensi/kelola/laporan-bulanan-pegawai")
    Call<ArrayList<MonthPresenceModel>> getLaporanBulanan(@Body PresenceRequestForm body);

    /*pagination */
    @GET("/api/v3/presensi/kelola/laporan-harian-absen")
    Call<PaginationModel<HarianGroupModel>> getLaporanHarianAbsenWithPaging(
            @Query("tgl") String tgl,
            @Query("sts_kehadiran") int sts_kehadiran,
            @Query("page") long page,
            @Query("page_size") int pageSize);

    @GET("/api/v3/presensi/kelola/laporan-harian-grup")
    Call<PaginationModel<HarianGroupModel>> getLaporanHarianGrupWithPaging(
            @Query("tgl") String tgl,
            @Query("grup_id") int grup_id,
            @Query("page") long page,
            @Query("page_size") int pageSize);

    @GET("/api/v3/presensi/sinkronisasi/presensi-log-harian")
    Call<PaginationModel<ChecklogModel>> getChecklog(
            @Query("tgl") String tgl,
            @Query("page") long page,
            @Query("page_size") int pageSize);





}
