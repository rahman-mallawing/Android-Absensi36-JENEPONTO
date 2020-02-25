package com.si.uinam.absensi36restfull.views.identitypagination.viewmodel;

import android.content.Context;
import android.util.Log;

import com.si.uinam.absensi36restfull.helpers.ApiTool;
import com.si.uinam.absensi36restfull.models.HarianGroupModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identity.IdentityGroup;
import com.si.uinam.absensi36restfull.views.identitypagination.service.PagingIdentityService;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PagingIdentityViewModel extends ViewModel {

    private int page;
    private MutableLiveData<PaginationModel<HarianGroupModel>> paginationModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PaginationModel<HarianGroupModel>> paginationFirstPage = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void loadFirstPage(Context context, AuthenticationListener authenticationListener, String tgl, IdentityGroup identityGroup) {
        this.page = 1;
        Log.d("PAGING-SERVICES", "loadFirstPage: ");
        if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.GROUP_IDENTITY){
            Log.d("TYPE-GROUP", identityGroup.getInfo()+" : "+ identityGroup.getGroup_id());
            loadHarianGrupList(context, authenticationListener, identityGroup.getGroup_id(), tgl, 1);
        }else if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.PRESENCE_IDENTITY){
            Log.d("TYPE-GROUP", identityGroup.getInfo()+" : "+ identityGroup.getSts_kehadiran());
            loadHarianAbsenList(context, authenticationListener, identityGroup.getSts_kehadiran(), tgl, 1);
        }
    }

    public void loadNextPage(Context context, AuthenticationListener authenticationListener, String tgl, IdentityGroup identityGroup, int nextPage) {
        this.page = nextPage;
        Log.d("PAGING-SERVICES", "loadFirstPage: ");
        if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.GROUP_IDENTITY){
            Log.d("TYPE-GROUP", identityGroup.getInfo()+" : "+ identityGroup.getGroup_id());
            loadHarianGrupList(context, authenticationListener, identityGroup.getGroup_id(), tgl, page);
        }else if(identityGroup.getGROUP_TYPE()==IdentityGroup.TYPE.PRESENCE_IDENTITY){
            Log.d("TYPE-GROUP", identityGroup.getInfo()+" : "+ identityGroup.getSts_kehadiran());
            loadHarianAbsenList(context, authenticationListener, identityGroup.getSts_kehadiran(), tgl, page);
        }
    }

    private void loadHarianGrupList(Context context, AuthenticationListener authenticationListener, int groupId, String tgl,  int pageList){
        PagingIdentityService.create(context, authenticationListener)
                .setCallback(new PagingIdentityService.PagingServiceCallbackInterface() {
                    @Override
                    public void onPostExecute(PaginationModel<HarianGroupModel> groupModelPaginationModel) {
                        if(page == 1){
                            paginationFirstPage.setValue(groupModelPaginationModel);
                        }else{
                            paginationModelMutableLiveData.setValue(groupModelPaginationModel);
                        }
                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParam(groupId, tgl, page)
                .execute();
    }

    private void loadHarianAbsenList(Context context, AuthenticationListener authenticationListener, int stsKehadiran, String tgl,  int pageAbsen){
        PagingIdentityService.create(context, authenticationListener)
                .setCallback(new PagingIdentityService.PagingServiceCallbackInterface() {
                    @Override
                    public void onPostExecute(PaginationModel<HarianGroupModel> groupModelPaginationModel) {
                        if(page == 1){
                            paginationFirstPage.setValue(groupModelPaginationModel);
                        }else{
                            paginationModelMutableLiveData.setValue(groupModelPaginationModel);
                        }

                    }

                    @Override
                    public void onFailure(String err) {
                        errorMessage.setValue(err);
                    }
                })
                .setDateParamAbsenIdentity(stsKehadiran, tgl, page)
                .executeAbsenIdentity();
    }

    public LiveData<PaginationModel<HarianGroupModel>> getPagingFirstPage() {
        return paginationFirstPage;
    }

    public LiveData<PaginationModel<HarianGroupModel>> getPagingHarianGrupList() {
        return paginationModelMutableLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
