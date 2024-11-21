package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.data.model.Seguimiento;

import java.util.List;

@Dao
public interface SeguimientoDAO {
    @Query("select * from seguimiento")
    LiveData<List<Seguimiento>> findAll();

    @Query("select * from seguimiento where id=:id")
    LiveData<Seguimiento> findById(Long id);

    @Insert
    void save(Seguimiento... seguimientos);

    @Delete
    void delete(Seguimiento seguimiento);

    @Update()
    void update(Seguimiento seguimiento);
}
