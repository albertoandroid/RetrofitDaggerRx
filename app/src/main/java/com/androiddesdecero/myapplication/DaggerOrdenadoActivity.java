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
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaggerOrdenadoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonajeAdapter adapter;
    private List<Personaje> personajes;

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

        Call<Data> call = client.getPersonajes();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                adapter.setData(response.body().getResults());
                List<Personaje> personajes = response.body().getResults();
                nuevaPeticion(response.body().getNext(), personajes);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "Error: " + t.getMessage());
            }
        });
    }

    private void nuevaPeticion(String url, List<Personaje> personajes1){
        String[] endPoint = url.split("https://swapi.co/api/");
        Call<Data> call = client.getPersonajes(endPoint[1]);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                List<Personaje> personajeList = response.body().getResults();
                personajes1.addAll(personajeList);

                Collections.sort(personajes1, new Comparator<Personaje>() {
                    @Override
                    public int compare(Personaje o1, Personaje o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });


                adapter.setData(personajes1);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "Error: " + t.getMessage());
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
}

