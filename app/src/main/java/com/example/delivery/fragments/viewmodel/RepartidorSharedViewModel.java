package com.example.delivery.fragments.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.delivery.model.Repartidor;

public class RepartidorSharedViewModel extends ViewModel {
    private final MutableLiveData<Repartidor> repartidor=new MutableLiveData<>();

    public void setRepartidor(Repartidor r){
        repartidor.setValue(r);
    }

    public LiveData<Repartidor> getRepartidor(){
        return repartidor;
    }

    public void clearRepartidor() {
        repartidor.setValue(null);
    }
}
