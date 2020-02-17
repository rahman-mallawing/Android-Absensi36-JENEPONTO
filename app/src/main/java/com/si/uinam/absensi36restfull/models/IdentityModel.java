package com.si.uinam.absensi36restfull.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class IdentityModel implements Parcelable {

    @SerializedName("nap")
    protected String nap;

    @SerializedName("ni")
    protected String ni;

    @SerializedName("user_id")
    protected int userId;

    @SerializedName("nama")
    protected String name;

    @SerializedName("tempat_lahir")
    protected String tempatLahir;

    @SerializedName("tanggal_lahir")
    protected Date tanggalLahir;

    @SerializedName("alamat")
    protected String alamat;

    @SerializedName("telpon")
    protected String telpon;

    @SerializedName("hp")
    protected String hp;

    @SerializedName("email")
    protected String email;

    @SerializedName("lhkpn")
    protected int lhkpn;

    @SerializedName("lhksn")
    protected int lhksn;

    @SerializedName("foto")
    protected String foto;

    @SerializedName("id_grup")
    protected int grupId;

    @SerializedName("id_jabatan")
    protected int jabatanId;

    @SerializedName("id_gol")
    protected int golId;

    @SerializedName("status")
    protected boolean status;

    @SerializedName("shift")
    protected boolean shift;

    @SerializedName("kode_jam")
    protected String kodeJam;

    @SerializedName("ket")
    protected String ket;

    @SerializedName("retired")
    protected boolean retired;

    public String getNap() {
        return nap;
    }

    public String getNi() {
        return ni;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelpon() {
        return telpon;
    }

    public String getHp() {
        return hp;
    }

    public String getEmail() {
        return email;
    }

    public int getLhkpn() {
        return lhkpn;
    }

    public int getLhksn() {
        return lhksn;
    }

    public String getFoto() {
        return foto;
    }

    public int getGrupId() {
        return grupId;
    }

    public int getJabatanId() {
        return jabatanId;
    }

    public int getGolId() {
        return golId;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isShift() {
        return shift;
    }

    public String getKodeJam() {
        return kodeJam;
    }

    public String getKet() {
        return ket;
    }

    public boolean isRetired() {
        return retired;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nap);
        dest.writeString(this.ni);
        dest.writeInt(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.tempatLahir);
        dest.writeLong(this.tanggalLahir != null ? this.tanggalLahir.getTime() : -1);
        dest.writeString(this.alamat);
        dest.writeString(this.telpon);
        dest.writeString(this.hp);
        dest.writeString(this.email);
        dest.writeInt(this.lhkpn);
        dest.writeInt(this.lhksn);
        dest.writeString(this.foto);
        dest.writeInt(this.grupId);
        dest.writeInt(this.jabatanId);
        dest.writeInt(this.golId);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeByte(this.shift ? (byte) 1 : (byte) 0);
        dest.writeString(this.kodeJam);
        dest.writeString(this.ket);
        dest.writeByte(this.retired ? (byte) 1 : (byte) 0);
    }

    public IdentityModel() {
    }

    protected IdentityModel(Parcel in) {
        this.nap = in.readString();
        this.ni = in.readString();
        this.userId = in.readInt();
        this.name = in.readString();
        this.tempatLahir = in.readString();
        long tmpTanggalLahir = in.readLong();
        this.tanggalLahir = tmpTanggalLahir == -1 ? null : new Date(tmpTanggalLahir);
        this.alamat = in.readString();
        this.telpon = in.readString();
        this.hp = in.readString();
        this.email = in.readString();
        this.lhkpn = in.readInt();
        this.lhksn = in.readInt();
        this.foto = in.readString();
        this.grupId = in.readInt();
        this.jabatanId = in.readInt();
        this.golId = in.readInt();
        this.status = in.readByte() != 0;
        this.shift = in.readByte() != 0;
        this.kodeJam = in.readString();
        this.ket = in.readString();
        this.retired = in.readByte() != 0;
    }

    public static final Parcelable.Creator<IdentityModel> CREATOR = new Parcelable.Creator<IdentityModel>() {
        @Override
        public IdentityModel createFromParcel(Parcel source) {
            return new IdentityModel(source);
        }

        @Override
        public IdentityModel[] newArray(int size) {
            return new IdentityModel[size];
        }
    };
}
