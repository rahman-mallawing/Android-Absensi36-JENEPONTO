package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

public class StaBulananTahunModel {
    @SerializedName("bulan")
    protected int bulan;

    @SerializedName("jumlah_grup")
    protected int jumlahGrup;

    @SerializedName("jumlah_pegawai")
    protected int jumlahPegawai;

    @SerializedName("jumlah_hari")
    protected int jumlahHari;

    @SerializedName("hari_x_pegawai")
    protected int hariXPegawai;

    @SerializedName("libur_x_pegawai")
    protected int liburXPegawai;

    @SerializedName("absen_x_pegawai")
    protected int absenXPegawai;

    @SerializedName("hadir_x_pegawai")
    protected int hadirXPegawai;

    @SerializedName("dinas_luar_x_pegawai")
    protected int dinasLuarXPegawai;

    @SerializedName("cuti_x_pegawai")
    protected int cutiXPegawai;

    @SerializedName("izin_x_pegawai")
    protected int izinXPegawai;

    @SerializedName("sakit_x_pegawai")
    protected int sakitXPegawai;

    @SerializedName("lain_lain_x_pegawai")
    protected int lainLainXPegawai;

    @SerializedName("menit_terlambat_x_pegawai")
    protected int menitTerlambatXPegawai;

    @SerializedName("menit_cp_x_pegawai")
    protected int menitCpXPegawai;

    @SerializedName("terlambat_x_pegawai")
    protected int terlambatXPegawai;

    @SerializedName("cp_x_pegawai")
    protected int cpXPegawai;


    public int getBulan() {
        return bulan;
    }

    public int getJumlahGrup() {
        return jumlahGrup;
    }

    public int getJumlahPegawai() {
        return jumlahPegawai;
    }

    public int getJumlahHari() {
        return jumlahHari;
    }

    public int getHariXPegawai() {
        return hariXPegawai;
    }

    public int getLiburXPegawai() {
        return liburXPegawai;
    }

    public int getAbsenXPegawai() {
        return absenXPegawai;
    }

    public int getHadirXPegawai() {
        return hadirXPegawai;
    }

    public int getDinasLuarXPegawai() {
        return dinasLuarXPegawai;
    }

    public int getCutiXPegawai() {
        return cutiXPegawai;
    }

    public int getIzinXPegawai() {
        return izinXPegawai;
    }

    public int getSakitXPegawai() {
        return sakitXPegawai;
    }

    public int getLainLainXPegawai() {
        return lainLainXPegawai;
    }

    public int getMenitTerlambatXPegawai() {
        return menitTerlambatXPegawai;
    }

    public int getMenitCpXPegawai() {
        return menitCpXPegawai;
    }

    public int getTerlambatXPegawai() {
        return terlambatXPegawai;
    }

    public int getCpXPegawai() {
        return cpXPegawai;
    }
}
