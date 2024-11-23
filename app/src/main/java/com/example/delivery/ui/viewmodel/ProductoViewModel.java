package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.ProductoDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Producto;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase ViewModel para manejar la lógica de negocio de la entidad `Producto`.
 * Conecta la base de datos con la interfaz de usuario para facilitar la gestión de datos.
 */
public class ProductoViewModel extends AndroidViewModel {

    // DAO para interactuar con la tabla `Producto`.
    private final ProductoDAO productoDAO;

    // ExecutorService para manejar operaciones en segundo plano.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor de la clase.
     * Inicializa el DAO y prepara el acceso a la base de datos.
     *
     * @param application Contexto de la aplicación.
     */
    public ProductoViewModel(@NonNull Application application) {
        super(application);
        // Obtenemos una instancia de la base de datos y el DAO de `Producto`.
        DatabaseApp db = DatabaseApp.getInstance(application);
        productoDAO = db.productoDAO();
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
            Log.e("ProductoViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("ProductoViewModel", "ExecutorService ya estaba cerrado");
        }
    }

    /**
     * Obtiene una lista reactiva de todos los productos.
     *
     * @return LiveData con la lista de productos.
     */
    public LiveData<List<Producto>> findAllProductos() {
        return productoDAO.findAll();
    }

    /**
     * Busca un producto específico por su ID.
     *
     * @param id Identificador del producto.
     * @return LiveData con el producto encontrado.
     */
    public LiveData<Producto> findById(Long id) {
        return productoDAO.findById(id);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param producto Objeto producto a guardar.
     */
    public void save(Producto producto) {
        executorService.execute(() -> {
            try {
                // Insertamos un nuevo producto en la base de datos.
                productoDAO.save(producto);
            } catch (Exception e) {
                Log.e("ProductoViewModel", "Error al guardar el producto", e);
            }
        });
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto Objeto producto con los datos actualizados.
     */
    public void update(Producto producto) {
        executorService.execute(() -> {
            try {
                // Actualizamos el producto en la base de datos.
                productoDAO.update(producto);
            } catch (Exception e) {
                Log.e("ProductoViewModel", "Error al actualizar el producto", e);
            }
        });
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * @param producto Objeto producto a eliminar.
     */
    public void delete(Producto producto) {
        executorService.execute(() -> {
            try {
                // Eliminamos el producto de la base de datos.
                productoDAO.delete(producto);
            } catch (Exception e) {
                Log.e("ProductoViewModel", "Error al eliminar el producto", e);
            }
        });
    }
}

/**
 * **Beneficios de esta clase:**
 *
 * - **Gestión eficiente de datos:** Proporciona una forma sencilla de interactuar con los datos
 *   de los productos sin acoplar la lógica de datos a la interfaz de usuario.
 * - **Reactividad:** Usa `LiveData` para que la UI se actualice automáticamente cuando los datos cambian.
 * - **Ejecución en segundo plano:** Las operaciones de base de datos se manejan en un hilo separado,
 *   lo que evita bloqueos en el hilo principal y mejora la experiencia del usuario.
 * - **Modularidad:** Centraliza toda la lógica relacionada con `Producto` en un solo lugar,
 *   facilitando su mantenimiento y pruebas.
 *   Uso práctico:**
 *  *    - Esta clase puede integrarse fácilmente en `Fragments` o `Activities` para mostrar listas de productos,
 *  *      buscar productos por su ID o realizar operaciones como agregar, actualizar o eliminar productos.
 *   */


/**
 * **Información sobre Room:**
 *
 * Room es una librería de persistencia para bases de datos SQLite en Android, que proporciona una
 * interfaz sencilla y eficiente para interactuar con la base de datos. Room convierte las clases de entidad,
 * como `Producto`, en tablas de base de datos, y permite realizar operaciones sin necesidad de escribir SQL
 * manualmente. Además, Room se integra con `LiveData`, lo que facilita la actualización en tiempo real de los
 * datos en la interfaz de usuario.
 *
 * Algunas de las características clave de Room incluyen:
 * - **Anotaciones:** Utiliza anotaciones como `@Entity`, `@PrimaryKey`, y `@ColumnInfo` para definir tablas y campos.
 * - **DAO:** A través de las interfaces `@Dao`, se pueden definir métodos de acceso a los datos, como `insert()`, `update()`, `delete()`, y consultas personalizadas.
 * - **Compatibilidad con LiveData:** Las consultas pueden devolver `LiveData`, lo que permite que la UI se actualice automáticamente.
 */
