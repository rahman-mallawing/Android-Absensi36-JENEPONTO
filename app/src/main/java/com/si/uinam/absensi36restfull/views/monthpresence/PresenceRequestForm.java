package com.si.uinam.absensi36restfull.views.monthpresence;

public class PresenceRequestForm {
    private String tgl;
    private int[] naps;

    public PresenceRequestForm(String tgl, int[] naps) {
        this.tgl = tgl;
        this.naps = naps;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public int[] getNaps() {
        return naps;
    }

    public void setNaps(int[] naps) {
        this.naps = naps;
    }
}
