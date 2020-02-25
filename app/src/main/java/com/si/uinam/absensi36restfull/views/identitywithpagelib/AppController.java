package com.si.uinam.absensi36restfull.views.identitywithpagelib;

import android.app.Application;
import android.content.Context;

import com.si.uinam.absensi36restfull.services.App;
import com.si.uinam.absensi36restfull.services.AuthenticationListener;
import com.si.uinam.absensi36restfull.views.identity.IdentityGroup;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


//import io.reactivex.Scheduler;
//import io.reactivex.schedulers.Schedulers;

public class AppController extends Application {

    //private RestApi restApi;
    private Scheduler scheduler;
    private AuthenticationListener authenticationListener;
    private IdentityGroup identityGroup;
    private String tgl;

    public IdentityGroup getIdentityGroup() {
        return identityGroup;
    }

    public void setIdentityGroup(IdentityGroup identityGroup) {
        this.identityGroup = identityGroup;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public void setAuthenticationListener(AuthenticationListener authenticationListener) {
        this.authenticationListener = authenticationListener;
    }

    private static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    public static AppController create(Context context, AuthenticationListener authenticationListener) {
        AppController app = AppController.get(context);
        app.setAuthenticationListener(authenticationListener);
        return app;
    }

    public App getApp(){
        return App.getAppInstance(this.getApplicationContext(), this.authenticationListener);
    }

    /*public RestApi getRestApi() {
        if(restApi == null) {
            restApi = RestApiFactory.create();
        }
        return restApi;
    }*/

    /*public void setRestApi(RestApi restApi) {
        this.restApi = restApi;
    }
*/
    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
