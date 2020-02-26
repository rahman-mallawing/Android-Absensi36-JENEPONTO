package com.si.uinam.absensi36restfull.views.checklogpage.viewmodel;

import com.si.uinam.absensi36restfull.views.checklogpage.datasource.ChecklogDataSource;
import com.si.uinam.absensi36restfull.views.checklogpage.datasource.factory.ChecklogDataFactory;
import com.si.uinam.absensi36restfull.views.checklogpage.model.ChecklogModel;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class ChecklogViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<ChecklogModel>> checklogLiveData;
    private AppController appController;

    public ChecklogViewModel(AppController appController) {
        this.appController = appController;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);
        ChecklogDataFactory checklogDataFactory = new ChecklogDataFactory(appController);
        networkState = Transformations.switchMap(checklogDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(20)
                        .setPageSize(20).build();

        checklogLiveData = (new LivePagedListBuilder(checklogDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<ChecklogModel>> getChecklogLiveData() {
        return checklogLiveData;
    }
}
