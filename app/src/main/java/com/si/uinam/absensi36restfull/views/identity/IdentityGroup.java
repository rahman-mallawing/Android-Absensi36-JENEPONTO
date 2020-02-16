package com.si.uinam.absensi36restfull.views.identity;

import android.os.Parcel;
import android.os.Parcelable;

public class IdentityGroup implements Parcelable {
    private TYPE GROUP_TYPE;
    private String info;
    private int group_id;
    private int sts_kehadiran;

    public enum TYPE {
        GROUP_IDENTITY, PRESENCE_IDENTITY
    }

    public TYPE getGROUP_TYPE() {
        return GROUP_TYPE;
    }

    public void setGROUP_TYPE(TYPE GROUP_TYPE) {
        this.GROUP_TYPE = GROUP_TYPE;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getSts_kehadiran() {
        return sts_kehadiran;
    }

    public void setSts_kehadiran(int sts_kehadiran) {
        this.sts_kehadiran = sts_kehadiran;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.GROUP_TYPE == null ? -1 : this.GROUP_TYPE.ordinal());
        dest.writeString(this.info);
        dest.writeInt(this.group_id);
        dest.writeInt(this.sts_kehadiran);
    }

    public IdentityGroup() {
    }

    protected IdentityGroup(Parcel in) {
        int tmpGROUP_TYPE = in.readInt();
        this.GROUP_TYPE = tmpGROUP_TYPE == -1 ? null : TYPE.values()[tmpGROUP_TYPE];
        this.info = in.readString();
        this.group_id = in.readInt();
        this.sts_kehadiran = in.readInt();
    }

    public static final Parcelable.Creator<IdentityGroup> CREATOR = new Parcelable.Creator<IdentityGroup>() {
        @Override
        public IdentityGroup createFromParcel(Parcel source) {
            return new IdentityGroup(source);
        }

        @Override
        public IdentityGroup[] newArray(int size) {
            return new IdentityGroup[size];
        }
    };
}
