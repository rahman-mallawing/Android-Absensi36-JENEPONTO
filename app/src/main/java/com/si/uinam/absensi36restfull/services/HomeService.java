package com.si.uinam.absensi36restfull.services;

import android.util.Log;

import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.viewmodels.home.HomeServiceCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeService {

    private String tgl;

    private App appService;
    private WeakReference<HomeServiceCallback> homeServiceCallbackWeakReference;

    public HomeService(App appService) {
        this.appService = appService;
    }

    public static HomeService create(AuthenticationListener authenticationListener) {
        return new HomeService(
                App.getAppInstance(authenticationListener)
        );
    }

    public HomeService setCallback(HomeServiceCallback homeServiceCallback) {
        this.homeServiceCallbackWeakReference = new WeakReference<>(homeServiceCallback);
        return this;
    }

    public HomeService setDateParam(String tgl) {
        this.tgl = tgl;
        return this;
    }

    public void execute(){
        Call<StatistikModel> call = appService.getApiService().
                getStatistikHarian(tgl);

        call.enqueue(new Callback<StatistikModel>() {
            @Override
            public void onResponse(Call<StatistikModel> call, Response<StatistikModel> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                //onResponseReceived(response.body());
                if(response.isSuccessful()){
                    //Intent loginIntent = new Intent(GroupFragment.this, LoginActivity.class);
                    //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                    //startActivity(loginIntent);
                    Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                    Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
                    HomeServiceCallback myListener = homeServiceCallbackWeakReference.get();
                    if(myListener != null){
                        myListener.onStatistikHarianExecute(response.body());
                    }
                }else{
                    onFailureExecuted(response.message());
                }
            }

            @Override
            public void onFailure(Call<StatistikModel> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

        Call<ArrayList<StaBulananTahunModel>> call2 = appService.getApiService()
                .getStatistikBulananTahun(tgl);

        call2.enqueue(new Callback<ArrayList<StaBulananTahunModel>>() {
            @Override
            public void onResponse(Call<ArrayList<StaBulananTahunModel>> call, Response<ArrayList<StaBulananTahunModel>> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                //onResponseReceived(response.body());
                if(response.isSuccessful()){
                    //Intent loginIntent = new Intent(GroupFragment.this, LoginActivity.class);
                    //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                    //startActivity(loginIntent);
                    Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                    Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
                    HomeServiceCallback myListener = homeServiceCallbackWeakReference.get();
                    if(myListener != null){
                        myListener.onStaBulanTahunExecute(response.body());
                    }
                }else{
                    onFailureExecuted(response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<StaBulananTahunModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }
        });

    }

    private void onFailureExecuted(String err) {
        HomeServiceCallback myListener = this.homeServiceCallbackWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }


}
