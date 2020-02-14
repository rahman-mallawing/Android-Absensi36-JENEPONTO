package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserModel {

    @SerializedName("id")
    protected int id;

    @SerializedName("nama_operator")
    protected String namaOperator;

    @SerializedName("name")
    protected String name;

    @SerializedName("alamat")
    protected String alamat;

    @SerializedName("email")
    protected String email;

    @SerializedName("hp")
    protected String hp;

    @SerializedName("aktif")
    protected boolean aktif;

    @SerializedName("web")
    protected int web;

    @SerializedName("android")
    protected int android;

    @SerializedName("verified_hp")
    protected int verifiedHp;

    @SerializedName("verified_email")
    protected int verifiedEmail;

    @SerializedName("absen_manual")
    protected int absenManual;

    @SerializedName("api_token")
    protected String apiToken;

    @SerializedName("grup_akses")
    protected int grupAkses;

    @SerializedName("akses_level")
    protected int aksesLevel;

    @SerializedName("photo")
    protected String photo;

    public int getId() {
        return id;
    }

    public String getNamaOperator() {
        return namaOperator;
    }

    public String getName() {
        return name;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getEmail() {
        return email;
    }

    public String getHp() {
        return hp;
    }

    public boolean isAktif() {
        return aktif;
    }

    public int getWeb() {
        return web;
    }

    public int getAndroid() {
        return android;
    }

    public int getVerifiedHp() {
        return verifiedHp;
    }

    public int getVerifiedEmail() {
        return verifiedEmail;
    }

    public int getAbsenManual() {
        return absenManual;
    }

    public String getApiToken() {
        return apiToken;
    }

    public int getGrupAkses() {
        return grupAkses;
    }

    public int getAksesLevel() {
        return aksesLevel;
    }

    public String getPhoto() {
        return photo;
    }
}
