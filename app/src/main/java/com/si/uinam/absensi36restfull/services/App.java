package com.si.uinam.absensi36restfull.services;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.models.UserModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private Session session;
    private ApiService apiService, loginService;
    private AuthenticationListener authenticationListener;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private static App app;

    public static App getAppInstance(Context context, AuthenticationListener authListener) {
        if (app == null) {
            app = new App();
        }
        app.setAuthenticationListener(authListener);
        app.setContext(context);
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Session getSession() {
        if(session==null){
            session = new Session() {
                @Override
                public boolean isLoggedIn() {
                    return ApiHelper.isLogged(getApplicationContext());
                }

                @Override
                public void saveUser(Context context, UserModel user) {
                    ApiHelper.saveUser(context, user);
                }

                @Override
                public String getToken() {
                    return ApiHelper.getApiKey(context);
                }

                @Override
                public int getTokenId() {
                    return ApiHelper.getUserId(context);
                }

                @Override
                public String getName() {
                    return ApiHelper.getApiName(getApplicationContext());
                }

                @Override
                public String getEmail() {
                    return ApiHelper.getApiEmail(getApplicationContext());
                }

                @Override
                public void invalidate() {
                    Log.d("TES-LOGOUT", "invalidate");
                    ApiHelper.invalidate(getApplicationContext());
                    if (authenticationListener != null) {
                        authenticationListener.onUserLoggedOut();
                    }
                }
            };
        }
        return session;
    }

    public void setAuthenticationListener(AuthenticationListener listener) {
        this.authenticationListener = listener;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            apiService = provideRetrofit(ApiHelper.getBaseUrl()).create(ApiService.class);
        }
        return apiService;
    }

    public ApiService getLoginService() {
        if (loginService == null) {
            loginService = provideLoginRetrofit(ApiHelper.getBaseUrl()).create(ApiService.class);
        }
        return loginService;
    }

    private Retrofit provideLoginRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideLoginOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private OkHttpClient provideLoginOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        return okhttpClientBuilder.build();
    }

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.addInterceptor(new AuthorizationInterceptor(getSession()));

        //HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        //okhttpClientBuilder.addInterceptor(loggingInterceptor);

        okhttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User-Agent", "Android-Mobile")
                        .header("Accept", "application/json")
                        .header("USER-ID", String.valueOf(getSession().getTokenId()))
                        .header("TOKEN", String.valueOf(getSession().getToken()))
                        .build();

                return chain.proceed(request);
            }
        });
        return okhttpClientBuilder.build();
    }
}
