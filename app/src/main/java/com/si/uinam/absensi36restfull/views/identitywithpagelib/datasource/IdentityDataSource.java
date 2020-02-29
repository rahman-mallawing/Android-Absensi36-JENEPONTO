package com.si.uinam.absensi36restfull.views.identitywithpagelib.datasource;


import android.util.Log;


import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.views.identityparcel.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IdentityDataSource extends PageKeyedDataSource<Long, HarianGroupModel> {

    private static final String TAG = IdentityDataSource.class.getSimpleName();

    private AppController appController;

    private long totalPage;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    private Call<PaginationModel<HarianGroupModel>> getApiRestCaller(Long page, int pageSize){
        IdentityGroup identityGroup = appController.getIdentityGroup();
        if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.GROUP_IDENTITY){
            Log.d("TYPE-GROUP", identityGroup.getInfo()+" : "+ identityGroup.getGroup_id());
            return appController.getApp().getApiService()
                    .getLaporanHarianGrupWithPaging(appController.getTgl(), identityGroup.getGroup_id(), page, pageSize);
        }else{
            Log.d("TYPE-PRESENCE", identityGroup.getInfo()+" : "+ identityGroup.getSts_kehadiran());
            return appController.getApp().getApiService()
                    .getLaporanHarianAbsenWithPaging(appController.getTgl(),identityGroup.getSts_kehadiran(),page, pageSize);
        }
    }

    public IdentityDataSource(AppController appController) {
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
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, HarianGroupModel> callback) {

        Log.i(TAG, "Loading loadInitial " + params.toString() + " Count " + params.requestedLoadSize);
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        getApiRestCaller(1l, params.requestedLoadSize)
                .enqueue(new Callback<PaginationModel<HarianGroupModel>>() {
                    @Override
                    public void onResponse(Call<PaginationModel<HarianGroupModel>> call, Response<PaginationModel<HarianGroupModel>> response) {
                        if(response.isSuccessful()) {
                            if(response.body().getArrayData().isEmpty()){
                                initialLoading.postValue(NetworkState.EMPTY_LOADED);
                                networkState.postValue(NetworkState.EMPTY_LOADED);
                                Log.i(TAG, "Loading Rang EMPTY");
                            }else{
                                totalPage = response.body().getLastPage();
                                Log.i(TAG, "Loading Rang TOTAL PAGE: "+totalPage);
                                callback.onResult(response.body().getArrayData(), null, 2l);
                                initialLoading.postValue(NetworkState.LOADED);
                                networkState.postValue(NetworkState.LOADED);
                                Log.i(TAG, "Loading Rang SUKSE");
                                Log.i(TAG, response.raw().toString());
                            }


                        } else {
                            Log.i(TAG, "Loading Rang USEKSES");
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<PaginationModel<HarianGroupModel>> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        Log.i(TAG, "Loading Rang FAILED");
                    }
                });



    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, HarianGroupModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, HarianGroupModel> callback) {
        Log.i(TAG, "Loading Rang After : " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        getApiRestCaller(params.key, params.requestedLoadSize)
                .enqueue(new Callback<PaginationModel<HarianGroupModel>>() {
                    @Override
                    public void onResponse(Call<PaginationModel<HarianGroupModel>> call, Response<PaginationModel<HarianGroupModel>> response) {
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
                    public void onFailure(Call<PaginationModel<HarianGroupModel>> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });


    }
}
