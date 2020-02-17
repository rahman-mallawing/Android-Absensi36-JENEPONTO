package com.si.uinam.absensi36restfull.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaginationModel<T> {

    @SerializedName("current_page")
    protected int currentPage;

    @SerializedName("data")
    protected ArrayList<T> arrayData;

    @SerializedName("first_page_url")
    protected String firstPageUrl;

    @SerializedName("from")
    protected int from;

    @SerializedName("last_page")
    protected int lastPage;

    @SerializedName("last_page_url")
    protected String lastPageUrl;

    @SerializedName("next_page_url")
    protected String nextPageUrl;

    @SerializedName("prev_page_url")
    protected String prevPageUrl;

    @SerializedName("path")
    protected String path;

    @SerializedName("per_page")
    protected int perPage;

    @SerializedName("to")
    protected int to;

    @SerializedName("total")
    protected int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public ArrayList<T> getArrayData() {
        return arrayData;
    }

    public String getFirstPageUrl() {
        return firstPageUrl;
    }

    public int getFrom() {
        return from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getLastPageUrl() {
        return lastPageUrl;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public String getPath() {
        return path;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }
}
