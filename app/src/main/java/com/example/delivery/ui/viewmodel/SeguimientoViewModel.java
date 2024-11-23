package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.SeguimientoDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Seguimiento;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase ViewModel para manejar la lógica de negocio de la entidad `Seguimiento`.
 * Conecta la base de datos con la interfaz de usuario para facilitar la gestión de datos.
 */
public class SeguimientoViewModel extends AndroidViewModel {

    // DAO para interactuar con la tabla `Seguimiento`.
    private final SeguimientoDAO seguimientoDAO;

    // ExecutorService para manejar operaciones en segundo plano.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor de la clase.
     * Inicializa el DAO y prepara el acceso a la base de datos.
     *
     * @param application Contexto de la aplicación.
     */
    public SeguimientoViewModel(@NonNull Application application) {
        super(application);
        // Obtenemos una instancia de la base de datos y el DAO de `Seguimiento`.
        DatabaseApp db = DatabaseApp.getInstance(application);
        seguimientoDAO = db.seguimientoDAO();
    }

    /**
     * Método que se llama cuando el ViewModel se destruye.
     * Cierra el ExecutorService para liberar recursos.
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (!executorService.isShutdown()) {
            // Cerramos el executor si aún está activo.
            executorService.shutdown();
            Log.e("SeguimientoViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("SeguimientoViewModel", "ExecutorService ya estaba cerrado");
        }
    }

    /**
     * Obtiene una lista reactiva de todos los seguimientos.
     *
     * @return LiveData con la lista de seguimientos.
     */
    public LiveData<List<Seguimiento>> findAllSeguimientos() {
        return seguimientoDAO.findAll();
    }

    /**
     * Busca un seguimiento específico por su ID.
     *
     * @param id Identificador del seguimiento.
     * @return LiveData con el seguimiento encontrado.
     */
    public LiveData<Seguimiento> findById(Long id) {
        return seguimientoDAO.findById(id);
    }

    /**
     * Guarda un nuevo seguimiento en la base de datos.
     *
     * @param seguimiento Objeto seguimiento a guardar.
     */
    public void save(Seguimiento seguimiento) {
        executorService.execute(() -> {
            try {
                // Insertamos un nuevo seguimiento en la base de datos.
                seguimientoDAO.save(seguimiento);
            } catch (Exception e) {
                Log.e("SeguimientoViewModel", "Error al guardar el seguimiento", e);
            }
        });
    }

    /**
     * Actualiza los datos de un seguimiento existente.
     *
     * @param seguimiento Objeto seguimiento con los datos actualizados.
     */
    public void update(Seguimiento seguimiento) {
        executorService.execute(() -> {
            try {
                // Actualizamos el seguimiento en la base de datos.
                seguimientoDAO.update(seguimiento);
            } catch (Exception e) {
                Log.e("SeguimientoViewModel", "Error al actualizar el seguimiento", e);
            }
        });
    }

    /**
     * Elimina un seguimiento de la base de datos.
     *
     * @param seguimiento Objeto seguimiento a eliminar.
     */
    public void delete(Seguimiento seguimiento) {
        executorService.execute(() -> {
            try {
                // Eliminamos el seguimiento de la base de datos.
                seguimientoDAO.delete(seguimiento);
            } catch (Exception e) {
                Log.e("SeguimientoViewModel", "Error al eliminar el seguimiento", e);
            }
        });
    }
}

/**
 * **Beneficios de esta clase:**
 *
 * - **Gestión eficiente de datos:** Esta clase permite manejar la lógica de negocio relacionada con la entidad `Seguimiento`,
 *   separando la lógica de datos de la interfaz de usuario para facilitar el mantenimiento y escalabilidad de la aplicación.
 * - **Reactividad:** Utiliza `LiveData` para garantizar que cualquier cambio en los seguimientos se refleje automáticamente en la interfaz de usuario.
 * - **Ejecución en segundo plano:** Las operaciones que requieren acceso a la base de datos se ejecutan en un hilo separado utilizando `ExecutorService`,
 *   lo que mejora el rendimiento y evita bloqueos en el hilo principal.
 * - **Modularidad:** Centraliza la lógica de negocio relacionada con `Seguimiento` en un único lugar, lo que facilita su reutilización y pruebas.
 *
 * **Información adicional relevante:**
 *
 * 1. **Relación con otras entidades:**
 *    - La entidad `Seguimiento` tiene una relación con `Pedido`, lo que indica que un seguimiento está asociado a un pedido específico.
 * 2. **Uso práctico:**
 *    - Esta clase puede ser utilizada para gestionar el seguimiento de pedidos en un sistema de entrega, permitiendo la actualización y visualización de su estado en tiempo real.
 * 3. **Escalabilidad:**
 *    - Si se desean agregar más funcionalidades o detalles al seguimiento, como nuevos campos o relaciones, esta clase está preparada para adaptarse a tales cambios de forma rápida y eficiente.
 */
