package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Negocio;

import java.util.List;

@Dao
public interface NegocioDAO {

    @Query("select * from negocio")
    LiveData<List<Negocio>> findAll();

    @Query("select * from negocio where id=:id")
    LiveData<Negocio> findById(Long id);

    @Insert
    Long save(Negocio negocio);

    @Delete
    void delete(Negocio negocio);

    @Update()
    void update(Negocio negocio);

    @Query("SELECT * FROM negocio WHERE id = :negocioId")
    LiveData<Negocio> getNegocioById(Long negocioId);
}
