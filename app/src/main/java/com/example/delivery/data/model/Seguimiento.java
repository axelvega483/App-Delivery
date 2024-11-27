package com.example.delivery.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.delivery.util.DateConverter;

import java.util.Date;

@Entity(tableName = "seguimiento",
foreignKeys = {
        @ForeignKey(
                entity = Pedido.class,
                parentColumns = "id",
                childColumns = "pedido_id"
        )
})
@TypeConverters({DateConverter.class})
public class Seguimiento {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "pedido_id")
    private Long pedidoId;
    private String estado;
    @ColumnInfo(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    public Seguimiento(Long pedidoId, String estado, Date fechaActualizacion) {
        this.pedidoId = pedidoId;
        this.estado = estado;
        this.fechaActualizacion = fechaActualizacion;
    }

    public Seguimiento() {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
