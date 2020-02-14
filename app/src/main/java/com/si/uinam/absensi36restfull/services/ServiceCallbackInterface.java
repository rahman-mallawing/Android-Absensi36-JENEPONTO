package com.si.uinam.absensi36restfull.services;

import java.util.ArrayList;

public interface ServiceCallbackInterface<T, D> {
    void onPostExecute(ArrayList<T> tArrayList);
    void onPostExecute(D tObject);
    void onFailure(String err);
}
