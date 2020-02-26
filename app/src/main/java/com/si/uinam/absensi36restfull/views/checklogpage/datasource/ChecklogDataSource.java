package com.si.uinam.absensi36restfull.views.checklogpage.datasource;

import android.util.Log;

import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.views.checklogpage.model.ChecklogModel;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklogDataSource extends PageKeyedDataSource<Long, ChecklogModel> {

    private static final String TAG = ChecklogDataSource.class.getSimpleName();

    private AppController appController;

    private long totalPage;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public ChecklogDataSource(AppController appController) {
        this.appController = appController;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, ChecklogModel> callback) {

        Log.i(TAG, "Loading loadInitial " + params.toString() + " Count " + params.requestedLoadSize);
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        appController.getApp().getApiService()
                .getChecklog(appController.getTgl(), 1l, params.requestedLoadSize)
                .enqueue(new Callback<PaginationModel<ChecklogModel>>() {
                    @Override
                    public void onResponse(Call<PaginationModel<ChecklogModel>> call, Response<PaginationModel<ChecklogModel>> response) {
                        if(response.isSuccessful()) {
                            totalPage = response.body().getLastPage();
                            Log.i(TAG, "Loading Rang TOTAL PAGE: "+totalPage);
                            callback.onResult(response.body().getArrayData(), null, 2l);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);
                            Log.i(TAG, "Loading Rang SUKSE");
                            Log.i(TAG, response.raw().toString());

                        } else {
                            Log.i(TAG, "Loading Rang USEKSES");
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<PaginationModel<ChecklogModel>> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        Log.i(TAG, "Loading Rang FAILED");
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ChecklogModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, ChecklogModel> callback) {

        Log.i(TAG, "Loading Rang After : " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        appController.getApp().getApiService()
                .getChecklog(appController.getTgl(), params.key, params.requestedLoadSize)
                .enqueue(new Callback<PaginationModel<ChecklogModel>>() {
                    @Override
                    public void onResponse(Call<PaginationModel<ChecklogModel>> call, Response<PaginationModel<ChecklogModel>> response) {
                        if(response.isSuccessful()) {
                            if(params.key < totalPage){
                                //long nextKey = (params.key ==  totalPage) ? null : params.key+1;
                                long nextKey = params.key+1;
                                callback.onResult(response.body().getArrayData(), nextKey);
                                networkState.postValue(NetworkState.LOADED);
                            }else {
                                networkState.postValue(NetworkState.LOADED);
                            }


                        } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                    @Override
                    public void onFailure(Call<PaginationModel<ChecklogModel>> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }
}
