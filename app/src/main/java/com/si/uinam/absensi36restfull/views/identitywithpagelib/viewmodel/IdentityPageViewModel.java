package com.si.uinam.absensi36restfull.views.identitywithpagelib.viewmodel;

import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.datasource.factory.IdentityDataFactory;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class IdentityPageViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<HarianGroupModel>> articleLiveData;
    private LiveData<NetworkState> initialState;

    private AppController appController;

    public IdentityPageViewModel(AppController appController) {
        this.appController =appController;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);
        IdentityDataFactory identityDataFactory = new IdentityDataFactory(appController);
        networkState = Transformations.switchMap(identityDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        initialState = Transformations.switchMap(identityDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getInitialLoading());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20).build();

        articleLiveData = (new LivePagedListBuilder(identityDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getInitialState() {
        return initialState;
    }

    public LiveData<PagedList<HarianGroupModel>> getArticleLiveData() {
        return articleLiveData;
    }

}
