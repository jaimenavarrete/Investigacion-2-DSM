package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

public class BebidaProducto extends Producto {
    public BebidaProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public double obtenerSubtotalProducto() {
        this.subtotal = this.precio * this.cantidad;

        return this.subtotal;
    }

    @Override
    public double obtenerTotalProducto() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}