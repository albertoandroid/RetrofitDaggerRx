package com.androiddesdecero.myapplication.di;

import android.app.Application;

public class BaseApplication extends Application {

    private RetrofitComponent retrofitComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule())
                .build();
    }

    public RetrofitComponent getRetrofitComponent(){
        return retrofitComponent;
    }
}
