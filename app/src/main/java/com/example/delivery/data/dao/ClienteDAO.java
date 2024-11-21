package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Cliente;

import java.util.List;

@Dao
public interface ClienteDAO {
    @Query("select * from cliente")
    LiveData<List<Cliente>> findAll();

    @Query("select * from cliente where id=:id")
    LiveData<Cliente> findById(Long id);

    @Insert
    void save(Cliente... clientes);

    @Delete
    void delete(Cliente cliente);

    @Update()
    void update(Cliente cliente);

}
