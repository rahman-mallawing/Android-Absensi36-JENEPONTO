package com.si.uinam.absensi36restfull.viewmodels.search;

import com.si.uinam.absensi36restfull.models.IdentityModel;
import com.si.uinam.absensi36restfull.models.PaginationModel;

public interface SearchServiceCallback {
    void onPostExecute(PaginationModel<IdentityModel> identityPaginationModel);
    void onFailure(String err);
}
