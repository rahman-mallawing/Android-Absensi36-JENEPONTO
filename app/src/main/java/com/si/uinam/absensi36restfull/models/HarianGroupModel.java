package com.si.uinam.absensi36restfull.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

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

    public int getRecordExist() {
        return recordExist;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setGrup(String grup) {
        this.grup = grup;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public void setJamMasuk(String jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public void setJamPulang(String jamPulang) {
        this.jamPulang = jamPulang;
    }

    public void setDay_off(int day_off) {
        this.day_off = day_off;
    }

    public void setHadir(int hadir) {
        this.hadir = hadir;
    }

    public void setAbsen(int absen) {
        this.absen = absen;
    }

    public void setDinasLuar(int dinasLuar) {
        this.dinasLuar = dinasLuar;
    }

    public void setCuti(int cuti) {
        this.cuti = cuti;
    }

    public void setIzin(int izin) {
        this.izin = izin;
    }

    public void setSakit(int sakit) {
        this.sakit = sakit;
    }

    public void setLainLain(int lainLain) {
        this.lainLain = lainLain;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public static DiffUtil.ItemCallback<HarianGroupModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<HarianGroupModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull HarianGroupModel oldItem, @NonNull HarianGroupModel newItem) {
            return newItem.getNap() == oldItem.getNap();
        }

        @Override
        public boolean areContentsTheSame(@NonNull HarianGroupModel oldItem, @NonNull HarianGroupModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        HarianGroupModel hgm = (HarianGroupModel) obj;
        return hgm.getNap() == this.getNap();
    }
}
