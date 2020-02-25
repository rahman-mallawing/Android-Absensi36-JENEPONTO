package com.si.uinam.absensi36restfull.views.identitywithpagelib.datasource.factory;


import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.AppController;
import com.si.uinam.absensi36restfull.views.identitywithpagelib.datasource.IdentityDataSource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class IdentityDataFactory extends DataSource.Factory {

    private MutableLiveData<IdentityDataSource> mutableLiveData;
    private IdentityDataSource identityDataSource;
    private AppController appController;

    public IdentityDataFactory(AppController appController) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<IdentityDataSource>();
    }

    @Override
    public DataSource create() {
        identityDataSource = new IdentityDataSource(appController);
        mutableLiveData.postValue(identityDataSource);
        return identityDataSource;
    }


    public MutableLiveData<IdentityDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
