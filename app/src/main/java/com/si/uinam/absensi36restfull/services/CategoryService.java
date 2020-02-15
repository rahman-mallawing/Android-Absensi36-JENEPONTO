package com.si.uinam.absensi36restfull.services;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.models.CategoryModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryService {

    private String tgl;

    private App categoryService;
    private WeakReference<ServiceCallbackInterface> serviceCallbackInterfaceWeakReference;

    public CategoryService(App categoryService) {
        this.categoryService = categoryService;
    }

    public static CategoryService create(Context context, AuthenticationListener authenticationListener) {
        return new CategoryService(
                App.getAppInstance(context, authenticationListener)
        );
    }

    public CategoryService setCallback(ServiceCallbackInterface serviceCallbackInterface) {
        this.serviceCallbackInterfaceWeakReference = new WeakReference<>(serviceCallbackInterface);
        return this;
    }

    public CategoryService setDateParam(String tgl) {
        this.tgl = tgl;
        return this;
    }

    public void loadBestCategory(){
        Call<ArrayList<CategoryModel>> call = categoryService.
                getApiService().
                getBestList(tgl);
        execute(call);
    }

    public void loadWorstCategory(){
        Call<ArrayList<CategoryModel>> call = categoryService.
                getApiService().
                getWorstList(tgl);
        execute(call);
    }

    private void execute(Call<ArrayList<CategoryModel>> call){
        call.enqueue(new Callback<ArrayList<CategoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryModel>> call, Response<ArrayList<CategoryModel>> response) {
                Log.d("RETROFIT-123456", response.raw().toString());
                if(response.isSuccessful()){
                    //Intent loginIntent = new Intent(GroupFragment.this, LoginActivity.class);
                    //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                    //startActivity(loginIntent);
                    Log.d("RETROFIT-TEST-ERROR2", response.body().toString());
                    onResponseReceived(response.body());
                }else{
                    onFailureExecuted(response.message());
                }

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
