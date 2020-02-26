package com.si.uinam.absensi36restfull.views.identitywithpagelib.utils;

public class NetworkState {

    public enum Status{
        RUNNING,
        SUCCESS,
        FAILED,
        EMPTY_LOADED
    }


    private final Status status;
    private final String msg;

    public static final NetworkState LOADED;
    public static final NetworkState EMPTY_LOADED;
    public static final NetworkState LOADING;

    public NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        LOADED=new NetworkState(Status.SUCCESS,"Success");
        LOADING=new NetworkState(Status.RUNNING,"Running");
        EMPTY_LOADED = new NetworkState(Status.EMPTY_LOADED,"INITIAL-EMPTY");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
