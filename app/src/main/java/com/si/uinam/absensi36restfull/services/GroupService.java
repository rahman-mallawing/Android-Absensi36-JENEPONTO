package com.si.uinam.absensi36restfull.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.si.uinam.absensi36restfull.LoginActivity;
import com.si.uinam.absensi36restfull.MainActivity;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.models.GroupModel;
import com.si.uinam.absensi36restfull.views.group.GroupFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupService {

    private String tgl;

    private App groupService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public GroupService(App groupService) {
        this.groupService = groupService;
    }

    public static GroupService create(AuthenticationListener authenticationListener) {
        return new GroupService(
                App.getAppInstance(authenticationListener)
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
        Call<ArrayList<GroupModel>> call = groupService.getApiService().
                getGroupList(tgl);
        //App app = new App();
        //app.setAuthenticationListener(this.context);
        //Call<ArrayList<GroupModel>> call = app.getApiService().getGroupList(tgl, USER_ID, API_KEY);
        call.enqueue(new Callback<ArrayList<GroupModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GroupModel>> call, Response<ArrayList<GroupModel>> response) {
               // Log.d("RETROFIT-123456", response.body().toString());
                Log.d("RETROFIT-123456", response.raw().toString());
                //onResponseReceived(response.body());
                if(response.isSuccessful()){
                    //Intent loginIntent = new Intent(GroupFragment.this, LoginActivity.class);
                    //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                    //startActivity(loginIntent);
                    Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                    onResponseReceived(response.body());
                }else{
                    onFailureExecuted(response.message());
                }
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
