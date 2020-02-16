package com.si.uinam.absensi36restfull.services;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.MonthPresenceModel;
import com.si.uinam.absensi36restfull.views.monthpresence.PresenceRequestForm;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonthPresenceService {

    private PresenceRequestForm jsonForm;
    private App appService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public MonthPresenceService(App appService) {
        this.appService = appService;
    }

    public static MonthPresenceService create(Context context, AuthenticationListener authenticationListener) {
        return new MonthPresenceService(
                App.getAppInstance(context,authenticationListener)
        );
    }

    public MonthPresenceService setCallback(ServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public MonthPresenceService setDateParam(String tgl, int[] naps) {
        this.jsonForm = new PresenceRequestForm(tgl, naps);
        return this;
    }

    public void execute(){

        Call<ArrayList<MonthPresenceModel>> call = appService.getApiService()
                .getLaporanBulanan(jsonForm);

        call.enqueue(new Callback<ArrayList<MonthPresenceModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MonthPresenceModel>> call, Response<ArrayList<MonthPresenceModel>> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                //onResponseReceived(response.body());
                if(response.isSuccessful()){
                    ServiceCallbackInterface myListener = serviceCallbackInterfaceWeakReference.get();
                    if(myListener != null){
                        myListener.onPostExecute(response.body());
                    }
                }else{
                    onFailureExecuted(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MonthPresenceModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }


    private void onFailureExecuted(String err) {
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }

}
