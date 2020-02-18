package com.si.uinam.absensi36restfull.viewmodels.search;

import android.content.Context;

import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.services.SearchService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<PaginationModel<IdentityModel>> paginationModelData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void loadPaginationIdentityData(Context context, AuthenticationListener authenticationListener, String query, int page){
        SearchService.create(context, authenticationListener)
                .setCallback(new SearchServiceCallback() {
                    @Override
                    public void onPostExecute(PaginationModel<IdentityModel> identityPaginationModel) {
                        paginationModelData.setValue(identityPaginationModel);
                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam("1", query, null, page)
                .execute();
    }

    public LiveData<PaginationModel<IdentityModel>> getIdentityPaginationData() {
        return paginationModelData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
