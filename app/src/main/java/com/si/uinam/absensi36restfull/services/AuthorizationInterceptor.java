package com.si.uinam.absensi36restfull.services;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private Session session;

    public AuthorizationInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        if (mainResponse.code() == 401 || mainResponse.code() == 403) {
            Log.d("TES-LOGOUT", "401 Code");
            session.invalidate();
        }
        return mainResponse;
    }
}
