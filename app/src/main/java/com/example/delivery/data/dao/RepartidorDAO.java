package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Repartidor;

import java.util.List;


@Dao
public interface RepartidorDAO {

    @Query("select * from repartidor")
    LiveData<List<Repartidor>> findAll();

    @Query("select * from repartidor where id=:id")
    LiveData<Repartidor> findById(Long id);

    @Insert
    void save(Repartidor... repartidors);

    @Delete
    void delete(Repartidor repartidor);

    @Query("delete from repartidor where id=:id")
    void deleteById(Long id);

    @Update()
    void update(Repartidor t);

    @Query("select * from repartidor where email=:email and password=:password")
    LiveData<Repartidor> findByEmailAndPassword(String email, String password);


}
