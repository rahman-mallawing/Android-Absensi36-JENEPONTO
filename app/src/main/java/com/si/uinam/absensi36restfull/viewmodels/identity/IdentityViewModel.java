package com.si.uinam.absensi36restfull.viewmodels.identity;

import android.content.Context;

import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.services.IdentityService;
import com.si.uinam.absensi36restfull.services.ServiceCallbackInterface;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IdentityViewModel extends ViewModel {
    private MutableLiveData<ArrayList<HarianGroupModel>> harianGrupList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void loadHarianGrupList(Context context, AuthenticationListener authenticationListener, int groupId, String tgl){
        IdentityService.create(context, authenticationListener)
                .setCallback(new ServiceCallbackInterface<HarianGroupModel, Object>() {
                    @Override
                    public void onPostExecute(ArrayList arrayList) {
                        harianGrupList.setValue(arrayList);
                    }

                    @Override
                    public void onPostExecute(Object tObject) {

                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(groupId, tgl)
                .execute();
    }

    public void loadHarianAbsenList(Context context, AuthenticationListener authenticationListener, int stsKehadiran, String tgl){
        IdentityService.create(context, authenticationListener)
                .setCallback(new ServiceCallbackInterface<HarianGroupModel, Object>() {
                    @Override
                    public void onPostExecute(ArrayList arrayList) {
                        harianGrupList.setValue(arrayList);
                    }

                    @Override
                    public void onPostExecute(Object tObject) {

                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParamAbsenIdentity(stsKehadiran, tgl)
                .execute();
    }

    public LiveData<ArrayList<HarianGroupModel>> getHarianGrupList() {
        return harianGrupList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
