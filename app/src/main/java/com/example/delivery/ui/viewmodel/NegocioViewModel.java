package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.NegocioDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Negocio;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase ViewModel para manejar la lógica de negocio de la entidad `Negocio`.
 * Proporciona un puente entre la base de datos y la interfaz de usuario (UI).
 */
public class NegocioViewModel extends AndroidViewModel {

    // DAO para interactuar con la base de datos de `Negocio`.
    private final NegocioDAO negocioDAO;

    // Servicio para ejecutar tareas en segundo plano y no bloquear el hilo principal.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor de la clase.
     * Inicializa la base de datos y el DAO para manejar las operaciones de `Negocio`.
     *
     * @param application Contexto de la aplicación.
     */
    public NegocioViewModel(@NonNull Application application) {
        super(application);
        // Obtenemos una instancia de la base de datos.
        DatabaseApp db = DatabaseApp.getInstance(application);
        // Asignamos el DAO para interactuar con la tabla `Negocio`.
        negocioDAO = db.negocioDAO();
    }

    /**
     * Método llamado cuando el ViewModel se destruye.
     * Cierra el `ExecutorService` para liberar recursos.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (!executorService.isShutdown()) {
            // Cerramos el executor si aún está activo.
            executorService.shutdown();
            Log.e("NegocioViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("NegocioViewModel", "ExecutorService ya estaba cerrado");
        }
    }

    /**
     * Obtiene una lista reactiva de todos los negocios.
     *
     * @return LiveData con la lista de negocios.
     */
    public LiveData<List<Negocio>> findAllNegocios() {
        return negocioDAO.findAll();
    }

    /**
     * Busca un negocio específico por su ID.
     *
     * @param id Identificador del negocio.
     * @return LiveData con el negocio encontrado.
     */
    public LiveData<Negocio> findById(Long id) {
        return negocioDAO.findById(id);
    }

    /**
     * Guarda un nuevo negocio en la base de datos.
     *
     * @param negocio Objeto negocio a guardar.
     */
    public void save(Negocio negocio) {
        executorService.execute(() -> {
            try {
                // Insertamos el nuevo negocio en la base de datos.
                negocioDAO.save(negocio);
            } catch (Exception e) {
                Log.e("NegocioViewModel", "Error al guardar el negocio", e);
            }
        });
    }

    /**
     * Actualiza los datos de un negocio existente.
     *
     * @param negocio Objeto negocio con los datos actualizados.
     */
    public void update(Negocio negocio) {
        executorService.execute(() -> {
            try {
                // Actualizamos el negocio en la base de datos.
                negocioDAO.update(negocio);
            } catch (Exception e) {
                Log.e("NegocioViewModel", "Error al actualizar el negocio", e);
            }
        });
    }

    /**
     * Elimina un negocio de la base de datos.
     *
     * @param negocio Objeto negocio a eliminar.
     */
    public void delete(Negocio negocio) {
        executorService.execute(() -> {
            try {
                // Eliminamos el negocio de la base de datos.
                negocioDAO.delete(negocio);
            } catch (Exception e) {
                Log.e("NegocioViewModel", "Error al eliminar el negocio", e);
            }
        });
    }
}

/**
 * **Beneficios de esta clase:**
 *
 * - **Desacoplamiento:** Mantiene la lógica de datos separada de la interfaz de usuario (UI).
 *   Esto hace que el código sea más modular y fácil de mantener.
 * - **Reactividad:** Usa `LiveData`, lo que permite que los cambios en los datos se reflejen automáticamente en la UI.
 * - **Eficiencia:** Las operaciones de base de datos se realizan en segundo plano
 *   usando `ExecutorService`, evitando bloquear el hilo principal y manteniendo
 *   la aplicación fluida para el usuario.
 * - **Facilidad de pruebas:** Al separar la lógica de negocio en un ViewModel,
 *   se facilita la realización de pruebas unitarias en los datos y procesos.
 * - **Reutilización:** Este ViewModel puede ser utilizado en múltiples `Activity` o `Fragment`,
 *   asegurando que las operaciones de negocio sean consistentes en toda la aplicación.
 */
