package com.cursojava.productos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codigoProducto;
    private String producto;
    private double preciounitario;
    private int stock;

    public Producto() {
    }

    public Producto(String producto, double preciounitario, int stock) {
        this.producto = producto;
        this.preciounitario = preciounitario;
        this.stock = stock;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(double preciounitario) {
        this.preciounitario = preciounitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
