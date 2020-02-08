package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

public class GroupModel {
    @SerializedName("id")
    protected int id;

    @SerializedName("grup")
    protected String grup;

    @SerializedName("jumlah_pegawai")
    protected int jumlahPegawai;

    @SerializedName("hadir")
    protected int hadir;

    @SerializedName("jumlah_terlambat")
    protected int jumlahTerlambat;

    @SerializedName("jumlah_cp")
    protected int jumlahCp;

    @SerializedName("total_menit_terlambat")
    protected int totalMenitTerlambat;

    @SerializedName("total_menit_cp")
    protected int totalMenitCp;

    @SerializedName("absen")
    protected int absen;

    @SerializedName("dinas_luar")
    protected int dinasLuar;

    @SerializedName("cuti")
    protected int cuti;

    @SerializedName("izin")
    protected int izin;

    @SerializedName("lain_lain")
    protected int lainLain;

    public int getId() {
        return id;
    }

    public String getGrup() {
        return grup;
    }

    public int getJumlahPegawai() {
        return jumlahPegawai;
    }

    public int getHadir() {
        return hadir;
    }

    public int getJumlahTerlambat() {
        return jumlahTerlambat;
    }

    public int getJumlahCp() {
        return jumlahCp;
    }

    public int getTotalMenitTerlambat() {
        return totalMenitTerlambat;
    }

    public int getTotalMenitCp() {
        return totalMenitCp;
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

    public int getLainLain() {
        return lainLain;
    }
}
