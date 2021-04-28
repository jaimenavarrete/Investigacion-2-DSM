package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

public class LimpiezaProducto extends Producto {

    public LimpiezaProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    @Override
    public double obtenerTotalProducto() {
        return 0;
    }

    @Override
    public double obtenerSubtotalProducto() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}
