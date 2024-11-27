package com.example.delivery.data.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.delivery.util.DateConverter;

import java.util.Date;

@Entity(tableName = "pedido", foreignKeys = {
        @ForeignKey(
                entity = Cliente.class,
                parentColumns = "id",
                childColumns = "cliente_id"
        ),
        @ForeignKey(
                entity = Negocio.class,
                parentColumns = "id",
                childColumns = "negocio_id"
        ),
        @ForeignKey(
                entity = Repartidor.class,
                parentColumns = "id",
                childColumns = "repartidor_id"
        )
})
@TypeConverters({DateConverter.class})
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    @ColumnInfo(name = "cliente_id")
    private Long clienteId;
    @ColumnInfo(name = "negocio_id")
    private Long negocioId;
    @ColumnInfo(name = "repartidor_id")
    private Long repartidorId;
    @ColumnInfo(name = "fecha_pedido")
    private Date fechaPedido;
    private String estado;

    public Pedido( Long clienteId, Long negocioId, Long repartidorId, Date fechaPedido, String estado) {
        this.clienteId = clienteId;
        this.negocioId = negocioId;
        this.repartidorId = repartidorId;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }

    public Long getRepartidorId() {
        return repartidorId;
    }

    public void setRepartidorId(Long repartidorId) {
        this.repartidorId = repartidorId;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
