package com.si.uinam.absensi36restfull.viewmodels;

import android.content.Context;

import com.si.uinam.absensi36restfull.models.MonthPresenceModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.services.MonthPresenceService;
import com.si.uinam.absensi36restfull.services.ServiceCallbackInterface;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonthPresenceViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MonthPresenceModel>> monthPresenceList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void loadMonthPresenceList(Context context, AuthenticationListener authenticationListener, String tgl, int[] naps){
        MonthPresenceService.create(context, authenticationListener)
                .setCallback(new ServiceCallbackInterface<MonthPresenceModel, Object>() {
                    @Override
                    public void onPostExecute(ArrayList<MonthPresenceModel> arrayList) {
                        monthPresenceList.setValue(arrayList);
                    }

                    @Override
                    public void onPostExecute(Object tObject) {

                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(tgl, naps)
                .execute();
    }


    public LiveData<ArrayList<MonthPresenceModel>> getMonthPresenceList() {
        return monthPresenceList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
