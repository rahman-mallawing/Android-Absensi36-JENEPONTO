package com.si.uinam.absensi36restfull.services;

import android.util.Log;

import com.si.uinam.absensi36restfull.models.CategoryModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryService {

    private static int USER_ID = 36;
    private static String API_KEY = "NYTpub4A3SW5ii1q6bemy20Qc5bCDLncdmoFUROsw1Z9m2JsPobpoQgj8xgsGUxMdBn4uhXHQxwsThp7JIyHG5qwD5ieNvfTYlMt";
    private Date tgl;

    private ApiEndPointInterface categoryService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public CategoryService(ApiEndPointInterface categoryService) {
        this.categoryService = categoryService;
    }

    public static CategoryService create() {
        return new CategoryService(
                RetrofitClientInstance.getRetrofitInstance()
                        .create(ApiEndPointInterface.class)
        );
    }

    public CategoryService setCallback(ServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public CategoryService setDateParam(Date tgl) {
        this.tgl = tgl;
        return this;
    }

    public void loadBestCategory(){
        Call<ArrayList<CategoryModel>> call = categoryService.getBestList(tgl, USER_ID, API_KEY);
        execute(call);
    }

    public void loadWorstCategory(){
        Call<ArrayList<CategoryModel>> call = categoryService.getWorstList(tgl, USER_ID, API_KEY);
        execute(call);
    }

    private void execute(Call<ArrayList<CategoryModel>> call){
        call.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                onResponseReceived(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryModel>> call, Throwable t) {
                Log.d("RETROFIT-TEST-ERROR", t.getMessage());
                onFailureExecuted(t.getMessage());
            }

        });
    }

    private void onResponseReceived(ArrayList<CategoryModel> categoryModels) {
        Log.i("ASYN_TAG", "onPreExecute inside DemoAsynch class");
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onPostExecute(categoryModels);
        }
    }

    private void onFailureExecuted(String err) {
        ServiceCallbackInterface myListener = this.serviceCallbackInterfaceWeakReference.get();
        if(myListener != null){
            myListener.onFailure(err);
        }
    }
}
