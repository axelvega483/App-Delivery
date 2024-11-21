package com.example.delivery.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "pedido_detalle",
        foreignKeys = {
                @ForeignKey(
                        entity = Pedido.class,
                        parentColumns = "id",
                        childColumns = "pedido_id"
                ),
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id",
                        childColumns = "producto_id"
                )
        })
public class PedidoDetalle {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "pedido_id")
    private Long pedidoId;
    @ColumnInfo(name = "producto_id")
    private Long productoId;
    private int cantidad;
    private double subtotal;

    public PedidoDetalle(Long id, Long pedidoId, Long productoId, int cantidad, double subtotal) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }


    public PedidoDetalle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}

