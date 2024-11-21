package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.PedidoDAO;
import com.example.delivery.data.dao.ProductoDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Pedido;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PedidosViewModel extends AndroidViewModel {
    private final PedidoDAO pedidoDAO;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public PedidosViewModel(@NonNull Application application) {
        super(application);
        DatabaseApp db = DatabaseApp.getInstance(application);
        pedidoDAO = db.pedidoDAO();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        //cierra el hilo
        executorService.shutdown();
        Log.e("Pedido","Eliminacion de hilo");
    }

    public LiveData<List<Pedido>> findAllPedidos() {
        return pedidoDAO.findAll();
    }

    public LiveData<Pedido> findById(Long id) {
        return pedidoDAO.findById(id);
    }

    public void save(Pedido pedido) {
        executorService.execute(() -> {
            try {
                pedidoDAO.save(pedido);
            } catch (Exception e) {
                Log.e("PedidosViewModel", "Error al guardar pedido", e);
            }
        });
    }


    public void delete(Pedido pedido) {
        executorService.execute(() -> {
            try {
                pedidoDAO.delete(pedido);
            } catch (Exception e) {
                Log.e("PedidosViewModel", "Error al eliminar pedido", e);
            }
        });
    }

    public void deleteById(Long id) {
        executorService.execute(() -> {
            try {
                pedidoDAO.deleteById(id);
            } catch (Exception e) {
                Log.e("PedidosViewModel", "Error al eliminar por id pedido", e);
            }
        });
    }


    public void update(Pedido pedido) {
        executorService.execute(() -> {
            try {
                pedidoDAO.update(pedido);
            } catch (Exception e) {
                Log.e("PedidosViewModel", "Error al actualizar pedido", e);
            }
        });
    }


}
