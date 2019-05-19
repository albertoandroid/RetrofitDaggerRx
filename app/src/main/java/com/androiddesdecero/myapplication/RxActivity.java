package com.androiddesdecero.myapplication;

import android.os.Bundle;
import android.util.Log;

import com.androiddesdecero.myapplication.adapter.PersonajeAdapter;
import com.androiddesdecero.myapplication.di.BaseApplication;
import com.androiddesdecero.myapplication.model.Data;
import com.androiddesdecero.myapplication.model.Personaje;
import com.androiddesdecero.myapplication.webservice.WebServiceClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RxActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonajeAdapter adapter;
    private List<Personaje> personajes;

    private Disposable disposable;

    @Inject
    WebServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Data>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                               disposable = d;
                            }

                            @Override
                            public void onNext(Data data) {
                                adapter.setData(data.getResults());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("TAG1", "Error: " + e.getMessage());

                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG1", "OnComplete: ");

                            }
                        }
                );
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
