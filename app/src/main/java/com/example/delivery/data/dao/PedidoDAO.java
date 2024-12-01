package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Pedido;

import java.util.List;

@Dao
public interface PedidoDAO {
    @Query("select * from pedido")
    LiveData<List<Pedido>> findAll();

    @Query("select * from pedido where id=:id")
    LiveData<Pedido> findById(Long id);

    @Insert
    Long save(Pedido pedidos);

    @Delete
    void delete(Pedido pedido);

    @Query("delete from pedido where id=:id")
    void deleteById(Long id);

    @Update()
    void update(Pedido pedido);

    @Query("select * from pedido")
    List<Pedido> findAllList();
    @Query("select * from pedido where estado='Pendiente'")
    LiveData<List<Pedido>> findAllPendiente();

}
