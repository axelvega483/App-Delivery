package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.PedidoDetalleDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.PedidoDetalle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase ViewModel para manejar la lógica de negocio de la entidad `PedidoDetalle`.
 * Facilita la interacción entre la interfaz de usuario y la base de datos.
 */
public class PedidoDetalleViewModel extends AndroidViewModel {

    // DAO para interactuar con la tabla `pedido_detalle`.
    private final PedidoDetalleDAO pedidoDetalleDAO;

    // ExecutorService para ejecutar tareas en segundo plano.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor de la clase.
     * Inicializa el DAO para acceder a los datos de `PedidoDetalle`.
     *
     * @param application Contexto de la aplicación.
     */
    public PedidoDetalleViewModel(@NonNull Application application) {
        super(application);
        // Obtiene la instancia de la base de datos y el DAO de `PedidoDetalle`.
        DatabaseApp db = DatabaseApp.getInstance(application);
        pedidoDetalleDAO = db.pedidoDetalleDAO();
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
            Log.e("PedidoDetalleViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("PedidoDetalleViewModel", "ExecutorService ya estaba cerrado");
        }
    }

    /**
     * Obtiene una lista reactiva de todos los detalles de pedido.
     *
     * @return LiveData con la lista de pedidos detallados.
     */
    public LiveData<List<PedidoDetalle>> findAllPedidoDetalles() {
        return pedidoDetalleDAO.findAll();
    }

    /**
     * Busca un detalle de pedido específico por su ID.
     *
     * @param id Identificador del detalle de pedido.
     * @return LiveData con el detalle de pedido encontrado.
     */
    public LiveData<PedidoDetalle> findById(Long id) {
        return pedidoDetalleDAO.findById(id);
    }

    /**
     * Guarda un nuevo detalle de pedido en la base de datos.
     *
     * @param pedidoDetalle Objeto detalle de pedido a guardar.
     */
    public void save(PedidoDetalle pedidoDetalle) {
        executorService.execute(() -> {
            try {
                // Insertamos un nuevo detalle de pedido en la base de datos.
                pedidoDetalleDAO.save(pedidoDetalle);
            } catch (Exception e) {
                Log.e("PedidoDetalleViewModel", "Error al guardar el detalle de pedido", e);
            }
        });
    }

    /**
     * Actualiza los datos de un detalle de pedido existente.
     *
     * @param pedidoDetalle Objeto detalle de pedido con los datos actualizados.
     */
    public void update(PedidoDetalle pedidoDetalle) {
        executorService.execute(() -> {
            try {
                // Actualizamos el detalle de pedido en la base de datos.
                pedidoDetalleDAO.update(pedidoDetalle);
            } catch (Exception e) {
                Log.e("PedidoDetalleViewModel", "Error al actualizar el detalle de pedido", e);
            }
        });
    }

    /**
     * Elimina un detalle de pedido de la base de datos.
     *
     * @param pedidoDetalle Objeto detalle de pedido a eliminar.
     */
    public void delete(PedidoDetalle pedidoDetalle) {
        executorService.execute(() -> {
            try {
                // Eliminamos el detalle de pedido de la base de datos.
                pedidoDetalleDAO.delete(pedidoDetalle);
            } catch (Exception e) {
                Log.e("PedidoDetalleViewModel", "Error al eliminar el detalle de pedido", e);
            }
        });
    }
}

/**
 * **Beneficios de esta clase:**
 *
 * - **Gestión eficiente de datos:** Permite gestionar los detalles de los pedidos sin mezclar la lógica de negocio con la interfaz de usuario.
 * - **Reactividad:** Utiliza `LiveData` para que los cambios en los detalles de los pedidos se reflejen automáticamente en la interfaz de usuario.
 * - **Ejecución en segundo plano:** Las operaciones de base de datos se ejecutan en un hilo separado utilizando `ExecutorService`, evitando bloqueos en el hilo principal.
 * - **Modularidad:** Centraliza la lógica de negocio de los detalles de pedido, lo que facilita su reutilización y mantenimiento.
 *
 * **Información adicional relevante:**
 *
 * 1. **Relación con otras entidades:**
 *    - `PedidoDetalle` tiene relaciones con las entidades `Pedido` y `Producto`, permitiendo rastrear los detalles de cada pedido con sus respectivos productos.
 * 3. **Escalabilidad:**
 *    - Esta clase puede ampliarse para agregar más detalles al pedido, como descuentos, impuestos o condiciones especiales de envío.
 */
