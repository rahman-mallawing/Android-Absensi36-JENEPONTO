package com.si.uinam.absensi36restfull.services;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.viewmodels.search.SearchServiceCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchService {

    private String query, nama, nap;
    private int page;
    private App appService;
    private WeakReference<SearchServiceCallback> serviceCallbackInterfaceWeakReference;

    public SearchService(App appService) {
        this.appService = appService;
    }

    public static SearchService create(Context context, AuthenticationListener authenticationListener) {
        return new SearchService(
                App.getAppInstance(context,authenticationListener)
        );
    }

    public SearchService setCallback(SearchServiceCallback serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public SearchService setDateParam(String query, String nama, String nap, int page) {
        this.query = query;
        this.nama = nama;
        this.nap = nap;
        this.page = page;
        return this;
    }

    public void execute(){

        Call<PaginationModel<IdentityModel>> call = appService.getApiService()
                .searchIdentity(query, nama, nap, page);

        call.enqueue(new Callback<PaginationModel<IdentityModel>>() {
            @Override
            public void onResponse(Call<PaginationModel<IdentityModel>> call, Response<PaginationModel<IdentityModel>> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                //onResponseReceived(response.body());
                if(response.isSuccessful()){
                    SearchServiceCallback myListener = serviceCallbackInterfaceWeakReference.get();
                    if(myListener != null){
                        myListener.onPostExecute(response.body());
                    }
                }else{
                    onFailureExecuted(response.message());
                }
            }

            @Override
            public void onFailure(Call<PaginationModel<IdentityModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }


    private void onFailureExecuted(String err) {
        SearchServiceCallback myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }
}
