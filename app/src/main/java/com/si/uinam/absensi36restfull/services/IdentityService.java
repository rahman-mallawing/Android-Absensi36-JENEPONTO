package com.si.uinam.absensi36restfull.services;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.HarianGroupModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdentityService {

    private String tgl;
    private int groupId;
    private int stsKehadiran;

    private App appService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public IdentityService(App appService) {
        this.appService = appService;
    }

    public static IdentityService create(Context context, AuthenticationListener authenticationListener) {
        return new IdentityService(
                App.getAppInstance(context,authenticationListener)
        );
    }

    public IdentityService setCallback(ServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public IdentityService setDateParam(int groupId, String tgl) {
        this.tgl = tgl;
        this.groupId = groupId;
        return this;
    }

    public IdentityService setDateParamAbsenIdentity(int stsKehadiran, String tgl) {
        this.tgl = tgl;
        this.stsKehadiran = stsKehadiran;
        return this;
    }

    public void execute(){

        Call<ArrayList<HarianGroupModel>> call = appService.getApiService().
                getLaporanHarianGrup(tgl, groupId);

        call.enqueue(new Callback<ArrayList<HarianGroupModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HarianGroupModel>> call, Response<ArrayList<HarianGroupModel>> response) {
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
            public void onFailure(Call<ArrayList<HarianGroupModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }

    public void executeAbsenIdentity(){

        Call<ArrayList<HarianGroupModel>> call = appService.getApiService().
                getLaporanHarianAbsen (tgl, stsKehadiran);

        call.enqueue(new Callback<ArrayList<HarianGroupModel>>() {
            @Override
            public void onResponse(Call<ArrayList<HarianGroupModel>> call, Response<ArrayList<HarianGroupModel>> response) {
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
            public void onFailure(Call<ArrayList<HarianGroupModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }

    private void onResponseReceived(ArrayList<HarianGroupModel> harianGroupModels) {
        Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onPostExecute(harianGroupModels);
        }
    }

    private void onFailureExecuted(String err) {
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }

}
