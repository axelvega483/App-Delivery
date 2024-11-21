package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.ClienteDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Pedido;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteDAO clienteDAO;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        clienteDAO = DatabaseApp.getInstance(application).clienteDAO();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        //cierra el hilo
        Log.e("Cliente","Eliminacion de hilo");
        executorService.shutdown();
    }

    public LiveData<List<Cliente>> findAllClientes() {
        return clienteDAO.findAll();
    }

    public LiveData<Cliente> findById(Long id) {
        return clienteDAO.findById(id);
    }

    public void save(Cliente cliente) {
        executorService.execute(() -> {
            try {
                clienteDAO.save(cliente);
            } catch (Exception e) {
                Log.e("ClienteViewModel", "Error al guardar cliente", e);
            }
        });
    }


    public void delete(Cliente cliente) {
        executorService.execute(() -> {
            try {
                clienteDAO.delete(cliente);
            } catch (Exception e) {
                Log.e("ClienteViewModel", "Error al eliminar cliente", e);
            }
        });
    }




    public void update(Cliente cliente) {
        executorService.execute(() -> {
            try {
                clienteDAO.update(cliente);
            } catch (Exception e) {
                Log.e("ClienteViewModel", "Error al actualizar cliente", e);
            }
        });
    }


}
