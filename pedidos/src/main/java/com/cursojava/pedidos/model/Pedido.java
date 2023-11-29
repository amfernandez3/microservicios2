package com.cursojava.pedidos.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido")
    private int idPedido;

    @Column(name = "codigoProducto")
    private int codigoProducto;

    @Column(name = "unidades")
    private int unidades;

    @Column(name = "total")
    private double total;

    @Column(name = "fechaPedido")
    private Date fechaPedido;

    // Constructor vacío
    public Pedido() {
    }

    // Constructor con todos los campos excepto id
    public Pedido(int codigoProducto, int unidades, double total, Date fechaPedido) {
        this.codigoProducto = codigoProducto;
        this.unidades = unidades;
        this.total = total;
        this.fechaPedido = fechaPedido;
    }

    // Setters y getters

    public int getIdPedido() {
        return idPedido;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
}
