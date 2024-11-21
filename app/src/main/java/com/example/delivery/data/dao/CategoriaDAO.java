package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Categoria;
import com.example.delivery.data.model.Cliente;

import java.util.List;

@Dao
public interface CategoriaDAO {
    @Query("select * from categoria")
    LiveData<List<Categoria>> findAll();

    @Query("select * from categoria where id=:id")
    LiveData<Categoria> findById(Long id);

    @Insert
    void save(Categoria... categorias);

    @Delete
    void delete(Categoria categoria);

    @Update()
    void update(Categoria categoria);
}
