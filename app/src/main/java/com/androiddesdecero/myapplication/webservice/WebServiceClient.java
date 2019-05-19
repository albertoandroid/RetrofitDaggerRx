package com.androiddesdecero.myapplication.webservice;

import com.androiddesdecero.myapplication.model.Data;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {

    @GET("people")
    Call<Data> getPersonajes();

    @GET()
    Call<Data> getPersonajes(@Url String url);

    @GET("people")
    Observable<Data> getPersonajesObservable();
}
