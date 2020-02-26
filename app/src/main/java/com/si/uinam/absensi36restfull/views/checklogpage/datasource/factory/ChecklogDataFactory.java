package com.si.uinam.absensi36restfull.views.checklogpage.datasource.factory;


import com.si.uinam.absensi36restfull.views.checklogpage.datasource.ChecklogDataSource;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ChecklogDataFactory extends DataSource.Factory {

    private MutableLiveData<ChecklogDataSource> mutableLiveData;
    private ChecklogDataSource checklogDataSource;
    private AppController appController;

    public ChecklogDataFactory(AppController appController) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<ChecklogDataSource>();
    }

    @Override
    public DataSource create() {
        checklogDataSource = new ChecklogDataSource(appController);
        mutableLiveData.postValue(checklogDataSource);
        return checklogDataSource;
    }

    public MutableLiveData<ChecklogDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
