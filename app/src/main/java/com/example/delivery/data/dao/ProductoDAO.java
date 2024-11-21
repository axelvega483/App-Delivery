package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Producto;

import java.util.List;

@Dao
public interface ProductoDAO {
    @Query("select * from producto")
    LiveData<List<Producto>> findAll();

    @Query("select * from producto where id=:id")
    LiveData<Producto> findById(Long id);

    @Insert
    void save(Producto... productos);

    @Delete
    void delete(Producto producto);

    @Update()
    void update(Producto producto);
}
