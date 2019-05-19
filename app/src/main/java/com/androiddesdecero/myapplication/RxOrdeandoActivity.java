package com.androiddesdecero.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.androiddesdecero.myapplication.adapter.PersonajeAdapter;
import com.androiddesdecero.myapplication.di.BaseApplication;
import com.androiddesdecero.myapplication.model.Data;
import com.androiddesdecero.myapplication.model.Personaje;
import com.androiddesdecero.myapplication.webservice.WebServiceClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RxOrdeandoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonajeAdapter adapter;
    private List<Personaje> personajes;

    private Disposable disposable;

    @Inject
    WebServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_ordenado);
        setUpDagger();
        setUpView();
        lanzarPeticion();
    }

    private void setUpDagger(){
        ((BaseApplication)getApplication()).getRetrofitComponent().inject(this);
    }
    private void lanzarPeticion(){

        client
                .getPersonajesObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Data, ObservableSource<Data>>() {
                    @Override
                    public ObservableSource<Data> apply(Data data) throws Exception {
                        Log.d("TAG1", "Hilo: " + Thread.currentThread().getName() + " flapmap");
                        personajes = data.getResults();
                        return client.getPersonajesObservable(data.getNext())
                                .subscribeOn(Schedulers.io());

                    }
                })
                .map(new Function<Data, Data>() {
                    @Override
                    public Data apply(Data data) throws Exception {
                        Log.d("TAG1", "Hilo: " + Thread.currentThread().getName() + " map");
                        List<Personaje> personajesList = data.getResults();
                        personajes.addAll(personajesList);

                        Collections.sort(personajes, new Comparator<Personaje>() {
                            @Override
                            public int compare(Personaje o1, Personaje o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        data.setResults(personajes);
                        return data;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Data>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Data data) {
                        Log.d("TAG1", "Hilo: " + Thread.currentThread().getName());
                        adapter.setData(data.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void setUpView(){
        personajes = new ArrayList<>();
        adapter = new PersonajeAdapter(personajes);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lim);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}


