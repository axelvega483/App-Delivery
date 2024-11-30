package com.example.delivery.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.data.model.Producto;

import java.util.List;

@Dao
public interface PedidoDetalleDAO {
    @Query("select * from pedido_detalle")
    LiveData<List<PedidoDetalle>> findAll();

    @Query("select * from pedido_detalle where id=:id")
    LiveData<PedidoDetalle> findById(Long id);

    @Insert
    Long save(PedidoDetalle pedidoDetalles);

    @Delete
    void delete(PedidoDetalle pedidoDetalle);

    @Update()
    void update(PedidoDetalle pedidoDetalle);

    @Query("SELECT * FROM producto WHERE id=:productoId")
    LiveData<Producto> getProductoById(Long productoId);

    @Query("select * from pedido_detalle")
    List<PedidoDetalle> findAllList();

    @Query("select * from pedido_detalle where pedido_id=:id")
    LiveData<List<PedidoDetalle>> findByPedido(Long id);
}
