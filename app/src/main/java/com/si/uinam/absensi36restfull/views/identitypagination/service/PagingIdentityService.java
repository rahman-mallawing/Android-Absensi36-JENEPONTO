package com.si.uinam.absensi36restfull.views.identitypagination.service;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.services.App;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingIdentityService {
    private String tgl;
    private int groupId;
    private int stsKehadiran;
    private int page;

    private App appService;
    private WeakReference<PagingServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public PagingIdentityService(App appService) {
        this.appService = appService;
    }

    public static PagingIdentityService create(Context context, AuthenticationListener authenticationListener) {
        return new PagingIdentityService(
                App.getAppInstance(context,authenticationListener)
        );
    }

    public PagingIdentityService setCallback(PagingServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public PagingIdentityService setDateParam(int groupId, String tgl, int page) {
        this.tgl = tgl;
        this.groupId = groupId;
        this.page = page;
        return this;
    }

    public PagingIdentityService setDateParamAbsenIdentity(int stsKehadiran, String tgl, int page) {
        this.tgl = tgl;
        this.stsKehadiran = stsKehadiran;
        this.page = page;
        return this;
    }

    public void execute(){

        Call<PaginationModel<HarianGroupModel>> call2 = appService.getApiService().
                getLaporanHarianGrupWithPaging(tgl, groupId, page, 20);
        Log.d("RETROFIT-PAGE", "ADA AKU ss");

        call2.enqueue(new Callback<PaginationModel<HarianGroupModel>>() {
            @Override
            public void onResponse(Call<PaginationModel<HarianGroupModel>> call, Response<PaginationModel<HarianGroupModel>> response) {
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
            public void onFailure(Call<PaginationModel<HarianGroupModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }

    public void executeAbsenIdentity(){

        Call<PaginationModel<HarianGroupModel>> call = appService.getApiService().
                getLaporanHarianAbsenWithPaging (tgl, stsKehadiran, page, 1);
        Log.d("RETROFIT-PAGE", "ADA AKU "+String.valueOf(page));

        call.enqueue(new Callback<PaginationModel<HarianGroupModel>>() {
            @Override
            public void onResponse(Call<PaginationModel<HarianGroupModel>> call, Response<PaginationModel<HarianGroupModel>> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                Log.d("RET33-TEST-ERROR3", response.raw().toString());
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
            public void onFailure(Call<PaginationModel<HarianGroupModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                Log.d("RET33-TEST-ERROR3", t.toString());
                t.printStackTrace();
                onFailureExecuted(t.getMessage());
            }
        });

    }

    private void onResponseReceived(PaginationModel<HarianGroupModel> harianGroupModelPaginationModel) {
        Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
        PagingServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onPostExecute(harianGroupModelPaginationModel);
        }
    }

    private void onFailureExecuted(String err) {
        PagingServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }

    public interface PagingServiceCallbackInterface {
        void onPostExecute(PaginationModel<HarianGroupModel> groupModelPaginationModel);
        void onFailure(String err);
    }

}
