package com.si.uinam.absensi36restfull.services;

import java.util.ArrayList;

public interface ServiceCallbackInterface<T> {
    void onPostExecute(ArrayList<T> tArrayList);
    void onFailure(String err);
}
