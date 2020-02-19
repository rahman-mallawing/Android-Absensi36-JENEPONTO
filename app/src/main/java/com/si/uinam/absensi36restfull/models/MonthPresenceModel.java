package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class MonthPresenceModel {

    @SerializedName("id_grup")
    protected int groupId;

    @SerializedName("grup")
    protected String grup;

    @SerializedName("nap")
    protected String nap;

    @SerializedName("user_id")
    protected int userId;

    @SerializedName("nama")
    protected String nama;

    @SerializedName("generated_tgl")
    protected Date tgl;

    @SerializedName("jam_masuk")
    protected String jamMasuk;

    @SerializedName("jam_pulang")
    protected String jamPulang;

    @SerializedName("ta_record_exist")
    protected int recordExist;

    @SerializedName("day_off")
    protected int day_off;

    @SerializedName("hadir")
    protected int hadir;

    @SerializedName("absen")
    protected int absen;

    @SerializedName("dinas_luar")
    protected int dinasLuar;

    @SerializedName("cuti")
    protected int cuti;

    @SerializedName("izin")
    protected int izin;

    @SerializedName("sakit")
    protected int sakit;

    @SerializedName("lain_lain")
    protected int lainLain;

    @SerializedName("foto")
    protected String foto;

    @SerializedName("ket_sts_absen")
    protected String ketStsAbsen;

    @SerializedName("ket")
    protected String ket;

    public int getGroupId() {
        return groupId;
    }

    public String getGrup() {
        return grup;
    }

    public String getNap() {
        return nap;
    }

    public int getUserId() {
        return userId;
    }

    public String getNama() {
        return nama;
    }

    public Date getTgl() {
        return tgl;
    }

    public String getJamMasuk() {
        return jamMasuk;
    }

    public String getJamPulang() {
        return jamPulang;
    }

    public int getDay_off() {
        return day_off;
    }

    public int getHadir() {
        return hadir;
    }

    public int getAbsen() {
        return absen;
    }

    public int getDinasLuar() {
        return dinasLuar;
    }

    public int getCuti() {
        return cuti;
    }

    public int getIzin() {
        return izin;
    }

    public int getSakit() {
        return sakit;
    }

    public int getLainLain() {
        return lainLain;
    }

    public String getFoto() {
        return foto;
    }

    public String getKetStsAbsen() {
        return ketStsAbsen;
    }

    public String getKet() {
        return ket;
    }

    public int getRecordExist() {
        return recordExist;
    }
}
