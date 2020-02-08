package com.si.uinam.absensi36restfull.viewmodels;

import com.si.uinam.absensi36restfull.models.GroupModel;
import com.si.uinam.absensi36restfull.services.GroupService;
import com.si.uinam.absensi36restfull.services.ServiceCallbackInterface;

import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<ArrayList<GroupModel>> groupList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();


    public void loadGroupList(Date tgl){
        GroupService.create()
                .setCallback(new ServiceCallbackInterface<GroupModel>() {
                    @Override
                    public void onPostExecute(ArrayList<GroupModel> arrayList) {
                        groupList.setValue(arrayList);
                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(tgl)
                .execute();
    }

    public LiveData<ArrayList<GroupModel>> getGroupList() {
        return groupList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}