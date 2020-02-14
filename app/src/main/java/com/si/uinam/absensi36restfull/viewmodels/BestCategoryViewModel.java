package com.si.uinam.absensi36restfull.viewmodels;

import com.si.uinam.absensi36restfull.models.CategoryModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.services.CategoryService;
import com.si.uinam.absensi36restfull.services.ServiceCallbackInterface;

import java.util.ArrayList;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BestCategoryViewModel extends ViewModel {

    private MutableLiveData<ArrayList<CategoryModel>> categoryList = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();


    public void loadBestCategoryList(AuthenticationListener authenticationListener, String tgl){
        loadCategoryList(authenticationListener, tgl).loadBestCategory();
    }

    private CategoryService loadCategoryList(AuthenticationListener authenticationListener, String tgl){
        return CategoryService.create(authenticationListener)
                .setCallback(new ServiceCallbackInterface<CategoryModel, Object>() {
                    @Override
                    public void onPostExecute(ArrayList<CategoryModel> arrayList) {
                        categoryList.setValue(arrayList);
                    }

                    @Override
                    public void onPostExecute(Object tObject) {

                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(tgl);
    }


    public LiveData<ArrayList<CategoryModel>> getCategoryList() {
        return categoryList;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
