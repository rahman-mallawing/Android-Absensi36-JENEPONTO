package com.si.uinam.absensi36restfull.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

public class HarianGroupModel implements Parcelable {


    @SerializedName("id_grup")
    protected int groupId;

    @SerializedName("grup")
    protected String grup;

    @SerializedName("nap")
    protected String nap;

    @SerializedName("nama")
    protected String nama;

    @SerializedName("generated_tgl")
    protected Date tgl;

    @SerializedName("jam_masuk")
    protected String jamMasuk;

    @SerializedName("jam_pulang")
    protected String jamPulang;

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

    public int getGroupId() {
        return groupId;
    }

    public String getGrup() {
        return grup;
    }

    public String getNap() {
        return nap;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.groupId);
        dest.writeString(this.grup);
        dest.writeString(this.nap);
        dest.writeString(this.nama);
        dest.writeLong(this.tgl != null ? this.tgl.getTime() : -1);
        dest.writeString(this.jamMasuk);
        dest.writeString(this.jamPulang);
        dest.writeInt(this.day_off);
        dest.writeInt(this.hadir);
        dest.writeInt(this.absen);
        dest.writeInt(this.dinasLuar);
        dest.writeInt(this.cuti);
        dest.writeInt(this.izin);
        dest.writeInt(this.sakit);
        dest.writeInt(this.lainLain);
        dest.writeString(this.foto);
    }

    public HarianGroupModel() {
    }

    protected HarianGroupModel(Parcel in) {
        this.groupId = in.readInt();
        this.grup = in.readString();
        this.nap = in.readString();
        this.nama = in.readString();
        long tmpTgl = in.readLong();
        this.tgl = tmpTgl == -1 ? null : new Date(tmpTgl);
        this.jamMasuk = in.readString();
        this.jamPulang = in.readString();
        this.day_off = in.readInt();
        this.hadir = in.readInt();
        this.absen = in.readInt();
        this.dinasLuar = in.readInt();
        this.cuti = in.readInt();
        this.izin = in.readInt();
        this.sakit = in.readInt();
        this.lainLain = in.readInt();
        this.foto = in.readString();
    }

    public static final Parcelable.Creator<HarianGroupModel> CREATOR = new Parcelable.Creator<HarianGroupModel>() {
        @Override
        public HarianGroupModel createFromParcel(Parcel source) {
            return new HarianGroupModel(source);
        }

        @Override
        public HarianGroupModel[] newArray(int size) {
            return new HarianGroupModel[size];
        }
    };
}
