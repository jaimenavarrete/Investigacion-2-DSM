package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

public abstract class Producto {
    public String nombre;
    public int cantidad;
    public double precio;

    public Producto() {}

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public abstract double obtenerSubtotalProducto();
    public abstract double obtenerTotalProducto();
}
