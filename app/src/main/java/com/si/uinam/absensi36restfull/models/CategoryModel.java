package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {

    @SerializedName("persen")
    protected double persen;

    @SerializedName("nap")
    protected String nap;

    @SerializedName("ni")
    protected String ni;

    @SerializedName("nama")
    protected String nama;

    @SerializedName("hp")
    protected String hp;

    @SerializedName("email")
    protected String email;

    @SerializedName("foto")
    protected String foto;

    public double getPersen() {
        return persen;
    }

    public String getNap() {
        return nap;
    }

    public String getNi() {
        return ni;
    }

    public String getNama() {
        return nama;
    }

    public String getHp() {
        return hp;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }
}
