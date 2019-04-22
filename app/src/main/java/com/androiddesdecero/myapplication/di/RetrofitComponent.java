package com.androiddesdecero.myapplication.di;

import com.androiddesdecero.myapplication.DaggerActivity;
import com.androiddesdecero.myapplication.DaggerOrdenadoActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(DaggerActivity daggerActivity);
    void inject(DaggerOrdenadoActivity daggerOrdenadoActivity);
}
