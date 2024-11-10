package com.example.delivery.model;


import java.util.Date;

public class Pedido {
    private Long id;
    private Cliente cliente;
    private Negocio negocio;
    private Repartidor repartidor;
    private Date fechaPedido;
    private String estado;

    public Pedido(Long id, Cliente cliente, Negocio negocio, Repartidor repartidor, Date fechaPedido, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.negocio = negocio;
        this.repartidor = repartidor;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }

  
  

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
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
