package com.test.nicolaguerrieri.facciamospesadesign;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deploygate.sdk.DeployGate;

public class App extends Application {

    private static App sInstance;

    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this);

        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;
    }

    public synchronized static App getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}