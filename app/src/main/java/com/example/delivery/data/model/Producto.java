package com.example.delivery.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.math.BigDecimal;

@Entity(tableName = "producto", foreignKeys = {
        @ForeignKey(
                entity = Negocio.class,
                parentColumns = "id",
                childColumns = "negocio_id"
        ),
        @ForeignKey(
                entity = Categoria.class,
                parentColumns = "id",
                childColumns = "categoria_id"
        )
})
public class Producto {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    @ColumnInfo(name = "negocio_id")
    private Long negocioId;
    @ColumnInfo(name = "categoria_id")
    private Long categoriaId;

    public Producto() {
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
