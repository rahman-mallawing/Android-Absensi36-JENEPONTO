package com.si.uinam.absensi36restfull.views.checklogpage.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class ChecklogModel {

    @SerializedName("id")
    protected int id;

    @SerializedName("checktime_id")
    protected int checktimeId;

    @SerializedName("serverid")
    protected String serverId;

    @SerializedName("userid")
    protected int userId;

    @SerializedName("name")
    protected String name;

    @SerializedName("employee_dept")
    protected String employeeDept;

    @SerializedName("employee_sup_dept")
    protected String employeeSupDept;

    @SerializedName("SN")
    protected String sn;

    @SerializedName("dev_alias")
    protected String devAlias;

    @SerializedName("dev_dept")
    protected String devDept;

    @SerializedName("NRP")
    protected String nrp;

    @SerializedName("checktime")
    protected String checkTime;

    @SerializedName("checktype")
    protected String checkType;

    @SerializedName("verifycode")
    protected String verifyCode;

    @SerializedName("sensorid")
    protected String sensorId;

    @SerializedName("created_at")
    protected String createdAt;

    public int getId() {
        return id;
    }

    public int getChecktimeId() {
        return checktimeId;
    }

    public String getServerId() {
        return serverId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmployeeDept() {
        return employeeDept;
    }

    public String getEmployeeSupDept() {
        return employeeSupDept;
    }

    public String getSn() {
        return sn;
    }

    public String getDevAlias() {
        return devAlias;
    }

    public String getDevDept() {
        return devDept;
    }

    public String getNrp() {
        return nrp;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public String getCheckType() {
        return checkType;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getSensorId() {
        return sensorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public static DiffUtil.ItemCallback<ChecklogModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ChecklogModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ChecklogModel oldItem, @NonNull ChecklogModel newItem) {
            return newItem.getChecktimeId() == oldItem.getChecktimeId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ChecklogModel oldItem, @NonNull ChecklogModel newItem) {
            return oldItem.equals(newItem);
        }
    };


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;

        ChecklogModel hgm = (ChecklogModel) obj;
        return hgm.getChecktimeId() == this.getChecktimeId();
    }

}
