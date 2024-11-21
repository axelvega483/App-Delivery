package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.PedidoDetalle;

import java.util.List;

@Dao
public interface PedidoDetalleDAO {
    @Query("select * from pedido_detalle")
    LiveData<List<PedidoDetalle>> findAll();

    @Query("select * from pedido_detalle where id=:id")
    LiveData<PedidoDetalle> findById(Long id);

    @Insert
    void save(PedidoDetalle... pedidoDetalles);

    @Delete
    void delete(PedidoDetalle pedidoDetalle);

    @Update()
    void update(PedidoDetalle pedidoDetalle);
}
