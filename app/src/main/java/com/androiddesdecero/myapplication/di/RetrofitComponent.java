package com.androiddesdecero.myapplication.di;

import com.androiddesdecero.myapplication.DaggerActivity;
import com.androiddesdecero.myapplication.DaggerOrdenadoActivity;
import com.androiddesdecero.myapplication.RxActivity;
import com.androiddesdecero.myapplication.RxOrdeandoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(DaggerActivity daggerActivity);
    void inject(DaggerOrdenadoActivity daggerOrdenadoActivity);

    void inject(RxActivity rxActivity);
    void inject(RxOrdeandoActivity rxOrdeandoActivity);
}
