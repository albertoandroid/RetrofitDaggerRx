package com.androiddesdecero.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;

public class RxBaseActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_base);

        crearObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(crearObserver());
    }

    private Observable crearObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
               try{
                  emitter.onNext("Primer Dato del Observable");
                  emitter.onNext("Segundo Dato del Observable");
                  emitter.onNext(tareaLargaDuracion());
                  emitter.onComplete();
               }catch (Exception e){
                   emitter.onError(e);
               }
            }
        });
    }

    private Observer crearObserver(){
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String data) {
                Log.d("TAG1", "onNext: " + data + " Hilo: " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("TAG1", "onComplete: " + " Hilo: " + Thread.currentThread().getName());
            }
        };
    }

    private String tareaLargaDuracion(){
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Log.d("TAG1", "Observable: Hilo: " + Thread.currentThread().getName());
        return "Tarea Larga Finalizada";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
