package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.delivery.data.dao.RepartidorDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Repartidor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepartidorViewModel extends AndroidViewModel {
    private final RepartidorDAO repartidorDAO;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Repartidor> repartidorLogueado = new MutableLiveData<>();

    public RepartidorViewModel(@NonNull Application application) {
        super(application);
        DatabaseApp db = DatabaseApp.getInstance(application);
        repartidorDAO = db.repartidorDAO();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            Log.e("ViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("ViewModel", "ExecutorService ya estaba cerrado");
        }
    }


    public LiveData<Repartidor> getRepartidorLogueado() {
        return repartidorLogueado;
    }

    public void setRepartidorLogueado(Repartidor repartidor) {
        repartidorLogueado.setValue(repartidor);
    }


    public LiveData<List<Repartidor>> findAllRepartidores() {
        return repartidorDAO.findAll();
    }

    public LiveData<Repartidor> findById(Long id) {
        return repartidorDAO.findById(id);
    }

    public LiveData<Repartidor> findByEmailAndPassword(String email, String password) {
        return repartidorDAO.findByEmailAndPassword(email, password);
    }

    public void save(Repartidor repartidor) {
        executorService.execute(() -> {
            try {
                repartidorDAO.save(repartidor);
            } catch (Exception e) {
                Log.e("RepartidorViewModel", "Error al guardar", e);
            }
        });
    }

    public void update(Repartidor repartidor) {
        executorService.execute(() -> {
            try {
                repartidorDAO.update(repartidor);
            } catch (Exception e) {
                Log.e("RepartidorViewModel", "Error al actualizar", e);
            }
        });
    }

    public void deleteById(Long id) {
        executorService.execute(() -> {
            try {
                repartidorDAO.deleteById(id);
            } catch (Exception e) {
                Log.e("RepartidorViewModel", "Error al eliminar", e);
            }
        });
    }


}
