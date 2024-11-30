package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.delivery.data.dao.RepartidorDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.Repartidor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Importancia de LiveData y ExecutorService
LiveData: Facilita la actualización automática de la UI cuando cambian los datos.
ExecutorService: Gestiona las operaciones en segundo plano, esencial para evitar bloqueos en el hilo principal*/

/*Este ViewModel encapsula toda la lógica de acceso y manipulación de datos de los repartidores, asegurándose de mantener
un ciclo de vida adecuado y evitando que las tareas pesadas bloqueen la UI. Utiliza principios de programación reactiva
mediante LiveData y maneja las operaciones asincrónicas con ExecutorService.*/

public class RepartidorViewModel extends AndroidViewModel {
    private final RepartidorDAO repartidorDAO;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); //Gestiona tareas en segundo plano para evitar que las operaciones de base de datos bloqueen el hilo principal (UI thread).
    private final MutableLiveData<Repartidor> repartidorLogueado = new MutableLiveData<>(); //Variable observable para almacenar el repartidor que ha iniciado sesión. La UI puede observar los cambios de estado de esta variable.
    private final MutableLiveData<Pedido> pedidoActual = new MutableLiveData<>();

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
        // Permite a la UI observar el repartidor actualmente logueado.
    }

    public void setRepartidorLogueado(Repartidor repartidor) {
        if (repartidor != null) {
            Log.e("setRepa", repartidor.toString());
            repartidorLogueado.setValue(repartidor);
            Log.e("setRepaLogueado", repartidorLogueado.getValue().toString());
        } else {
            Log.e("RepartidorViewModel", "Intento de establecer un repartidor nulo");
        }
    }


    //Estos métodos llaman a las funciones definidas en el DAO para obtener:

    public LiveData<List<Repartidor>> findAllRepartidores() {
        return repartidorDAO.findAll();
        //Todos los repartidores (findAllRepartidores).
    }

    public LiveData<Repartidor> findById(Long id) {
        return repartidorDAO.findById(id);
        //Un repartidor por su ID (findById).
    }

    public LiveData<Repartidor> findByEmailAndPassword(String email, String password) {
        return repartidorDAO.findByEmailAndPassword(email, password);
        //Un repartidor autenticado mediante email y contraseña (findByEmailAndPassword).
    }

    public void save(Repartidor repartidor) { //Guarda un nuevo repartidor en la base de datos
        executorService.execute(() -> { //Ejecuta el código en un hilo secundario para no bloquear la UI.
            try {
                repartidorDAO.save(repartidor);
            } catch (Exception e) {
                Log.e("RepartidorViewModel", "Error al guardar", e); //Captura y registra cualquier error durante la operación.
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


    public LiveData<Pedido> getPedidoActual() {
        return pedidoActual;
    }

    public void setPedidoActual(Pedido pedido) {
        if (pedidoActual != null) {
            pedidoActual.setValue(pedido);
            Log.e("setPedidoActual", pedidoActual.getValue().toString());
        } else {
            Log.e("RepartidorViewModel", "El pedido es nulo");
        }
    }
}
