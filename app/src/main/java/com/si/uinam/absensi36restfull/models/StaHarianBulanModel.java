package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StaHarianBulanModel {
    @SerializedName("day_of_month")
    protected int dayOfMonth;

    @SerializedName("tgl")
    protected Date tgl;

    @SerializedName("jumlah_grup")
    protected int jumlahGrup;

    @SerializedName("jumlah_pegawai")
    protected int jumlahPegawai;

    @SerializedName("libur")
    protected int libur;

    @SerializedName("absen")
    protected int absen;

    @SerializedName("hadir")
    protected int hadir;

    @SerializedName("breakin")
    protected int breakin;

    @SerializedName("breakout")
    protected int breakout;

    @SerializedName("terlambat")
    protected int terlambat;

    @SerializedName("menit_terlambat")
    protected int menitTerlambat;

    @SerializedName("cp")
    protected int cp;

    @SerializedName("menit_cp")
    protected int menitCp;

    @SerializedName("dinas_luar")
    protected int dinasLuar;

    @SerializedName("cuti")
    protected int cuti;

    @SerializedName("ijin")
    protected int izin;

    @SerializedName("sakit")
    protected int sakit;

    @SerializedName("lain_lain")
    protected int lainLain;

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public Date getTgl() {
        return tgl;
    }

    public int getJumlahGrup() {
        return jumlahGrup;
    }

    public int getJumlahPegawai() {
        return jumlahPegawai;
    }

    public int getLibur() {
        return libur;
    }

    public int getAbsen() {
        return absen;
    }

    public int getHadir() {
        return hadir;
    }

    public int getBreakin() {
        return breakin;
    }

    public int getBreakout() {
        return breakout;
    }

    public int getTerlambat() {
        return terlambat;
    }

    public int getMenitTerlambat() {
        return menitTerlambat;
    }

    public int getCp() {
        return cp;
    }

    public int getMenitCp() {
        return menitCp;
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
}
