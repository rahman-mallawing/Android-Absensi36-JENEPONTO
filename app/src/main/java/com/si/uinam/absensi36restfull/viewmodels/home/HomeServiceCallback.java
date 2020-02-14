package com.si.uinam.absensi36restfull.viewmodels.home;

import com.si.uinam.absensi36restfull.models.StaBulananTahunModel;
import com.si.uinam.absensi36restfull.models.StaHarianBulanModel;
import com.si.uinam.absensi36restfull.models.StatistikModel;

import java.util.ArrayList;

public interface HomeServiceCallback {
    void onStatistikHarianExecute(StatistikModel statistikModel);
    void onStaBulanTahunExecute(ArrayList<StaBulananTahunModel> staBulananTahunModels);
    void onStaHariBulanExecute(ArrayList<StaHarianBulanModel> staHarianBulanModels);
    void onFailure(String err);
}
