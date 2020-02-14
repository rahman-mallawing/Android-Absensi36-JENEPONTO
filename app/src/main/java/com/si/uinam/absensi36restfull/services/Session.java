package com.si.uinam.absensi36restfull.services;

import android.content.Context;

import com.si.uinam.absensi36restfull.models.UserModel;

public interface Session {
    boolean isLoggedIn();

    void saveUser(Context context, UserModel user);

    String getToken();

    int getTokenId();

    String getName();

    String getEmail();

    void invalidate();
}
