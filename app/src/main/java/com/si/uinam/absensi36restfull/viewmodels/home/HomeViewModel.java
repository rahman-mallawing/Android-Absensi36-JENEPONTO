package com.si.uinam.absensi36restfull.viewmodels.home;

import android.content.Context;

import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StaHarianBulanModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.services.HomeService;
import com.si.uinam.absensi36restfull.services.ServiceCallbackInterface;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<StatistikModel> statistikModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StaBulananTahunModel>> staBulananTahunList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<StaHarianBulanModel>> staHarianBulanList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void loadStatistik(Context context, AuthenticationListener authenticationListener, String tgl){
        HomeService.create(context, authenticationListener)
                .setCallback(new HomeServiceCallback() {
                    @Override
                    public void onStatistikHarianExecute(StatistikModel statistikModel) {
                        statistikModelMutableLiveData.setValue(statistikModel);
                    }

                    @Override
                    public void onStaBulanTahunExecute(ArrayList<StaBulananTahunModel> staBulananTahunModels) {
                        staBulananTahunList.setValue(staBulananTahunModels);
                    }

                    @Override
                    public void onStaHariBulanExecute(ArrayList<StaHarianBulanModel> staHarianBulanModels) {
                        staHarianBulanList.setValue(staHarianBulanModels);
                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(tgl)
                .execute();
    }

    public LiveData<StatistikModel> getStatistikHarian() {
        return statistikModelMutableLiveData;
    }

    public LiveData<ArrayList<StaBulananTahunModel>> getStaBulananTahunList(){
        return staBulananTahunList;
    }

    public LiveData<ArrayList<StaHarianBulanModel>> getStaHarianBulanList(){
        return staHarianBulanList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}