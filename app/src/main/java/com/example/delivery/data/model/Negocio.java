package com.example.delivery.data.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "negocio")
public class Negocio {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String cuit;
    @Embedded
    private Direccion direccion;
    private boolean estado;


    public Negocio( String nombre, String telefono, String email, String cuit, Direccion direccion, boolean estado) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.cuit = cuit;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Negocio() {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
