package com.si.uinam.absensi36restfull.services;

import android.util.Log;

import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.models.GroupModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupService {

    private static int USER_ID = 36;
    private static String API_KEY = "NYTpub4A3SW5ii1q6bemy20Qc5bCDLncdmoFUROsw1Z9m2JsPobpoQgj8xgsGUxMdBn4uhXHQxwsThp7JIyHG5qwD5ieNvfTYlMt";
    private String tgl;

    private ApiEndPointInterface groupService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public GroupService(ApiEndPointInterface groupService) {
        this.groupService = groupService;
    }

    public static GroupService create() {
        return new GroupService(
                RetrofitClientInstance.getRetrofitInstance()
                        .create(ApiEndPointInterface.class)
        );
    }

    public GroupService setCallback(ServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public GroupService setDateParam(String tgl) {
        this.tgl = tgl;
        return this;
    }

    public void execute(){
        Call<ArrayList<GroupModel>> call = groupService.getGroupList(tgl, USER_ID, API_KEY);
        call.enqueue(new Callback<ArrayList<GroupModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupModel>> call, Response<ArrayList<GroupModel>> response) {
                Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                onResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<GroupModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });
    }

    private void onResponseReceived(ArrayList<GroupModel> groupModels) {
        Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onPostExecute(groupModels);
        }
    }

    private void onFailureExecuted(String err) {
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }


}
