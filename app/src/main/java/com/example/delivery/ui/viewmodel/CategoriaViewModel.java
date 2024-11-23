package com.example.delivery.ui.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.delivery.data.dao.CategoriaDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Categoria;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Clase ViewModel para manejar la lógica de negocio de la entidad `Categoria`.
 * Conecta la base de datos con la interfaz de usuario para facilitar la gestión de datos.
 *
 * El ViewModel sirve como intermediario entre la capa de datos y la interfaz de usuario. Su función principal es:
 * Proveer datos reactivos: Facilita el acceso a los datos mediante LiveData, permitiendo que la interfaz de usuario se
 * actualice automáticamente cuando cambian los datos. Manejar lógica de negocio: Centraliza las operaciones relacionadas con
 * las categorías (consultar, guardar, actualizar y eliminar) fuera de la UI. Optimizar recursos: Realiza las operaciones de
 * base de datos en hilos secundarios, evitando bloquear el hilo principal y mejorando el rendimiento.
 * Mantener datos persistentes: Conserva los datos durante cambios de configuración, como rotaciones de pantalla, asegurando
 * que la UI no pierda información relevante.
 */
public class CategoriaViewModel extends AndroidViewModel {

    // DAO para interactuar con la tabla `Categoria`.
    private final CategoriaDAO categoriaDAO;

    // ExecutorService para manejar operaciones en segundo plano.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Constructor de la clase.
     * Inicializa el DAO y prepara el acceso a la base de datos.
     *
     * @param application Contexto de la aplicación.
     */
    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        // Obtenemos una instancia de la base de datos y el DAO de `Categoria`.
        DatabaseApp db = DatabaseApp.getInstance(application);
        categoriaDAO = db.categoriaDAO();
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
            Log.e("CategoriaViewModel", "ExecutorService cerrado correctamente");
        } else {
            Log.e("CategoriaViewModel", "ExecutorService ya estaba cerrado");
        }
    }

    /**
     * Obtiene una lista reactiva de todas las categorías.
     *
     * @return LiveData con la lista de categorías.
     */
    public LiveData<List<Categoria>> findAllCategorias() {
        return categoriaDAO.findAll();
    }

    /**
     * Busca una categoría específica por su ID.
     *
     * @param id Identificador de la categoría.
     * @return LiveData con la categoría encontrada.
     */
    public LiveData<Categoria> findById(Long id) {
        return categoriaDAO.findById(id);
    }

    /**
     * Guarda una nueva categoría en la base de datos.
     *
     * @param categoria Objeto categoría a guardar.
     */
    public void save(Categoria categoria) {
        executorService.execute(() -> {
            try {
                // Insertamos una nueva categoría en la base de datos.
                categoriaDAO.save(categoria);
            } catch (Exception e) {
                Log.e("CategoriaViewModel", "Error al guardar la categoría", e);
            }
        });
    }

    /**
     * Actualiza los datos de una categoría existente.
     *
     * @param categoria Objeto categoría con los datos actualizados.
     */
    public void update(Categoria categoria) {
        executorService.execute(() -> {
            try {
                // Actualizamos la categoría en la base de datos.
                categoriaDAO.update(categoria);
            } catch (Exception e) {
                Log.e("CategoriaViewModel", "Error al actualizar la categoría", e);
            }
        });
    }

    /**
     * Elimina una categoría de la base de datos.
     *
     * @param categoria Objeto categoría a eliminar.
     */
    public void delete(Categoria categoria) {
        executorService.execute(() -> {
            try {
                // Eliminamos la categoría de la base de datos.
                categoriaDAO.delete(categoria);
            } catch (Exception e) {
                Log.e("CategoriaViewModel", "Error al eliminar la categoría", e);
            }
        });
    }
}

/**
 * **Beneficios de esta clase:**
 *
 * - **Gestión eficiente de datos:** Proporciona una forma sencilla de interactuar con los datos
 *   de las categorías sin acoplar la lógica de datos a la interfaz de usuario.
 * - **Reactividad:** Usa `LiveData` para que la UI se actualice automáticamente cuando los datos cambian.
 * - **Ejecución en segundo plano:** Las operaciones de base de datos se manejan en un hilo separado,
 *   lo que evita bloqueos en el hilo principal y mejora la experiencia del usuario.
 * - **Modularidad:** Centraliza toda la lógica relacionada con `Categoria` en un solo lugar,
 *   facilitando su mantenimiento y pruebas.
 */
