package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

/*
 *
 * Esta clase está orientada a los productos que sean de tipo bebidas alcohólicas, y en este ejemplo
 * ficticio se toma en cuenta que la compra de bebidas alcohólicas no tiene un costo añadido a parte
 * del impuesto del 15%
 *
 * */

public class BebidaAlcoholicaProducto extends Producto {
    public BebidaAlcoholicaProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    /*
     *
     * Aquí vemos cómo la clase hija está redefiniendo los métodos obtenerSubtotalProducto y
     * obtenerTotalProducto para los productos de tipo bebidas alcohólicas, de modo que sea la misma
     * clase hija la encargada de definir la forma en la que se calcularán esos valores, para que no
     * tengamos que modificar el código de otras clases (por ejemplo la clase padre) cumpliendo así
     * el principio abierto/cerrado
     *
     * */

    @Override
    public double obtenerSubtotalProducto() {
        this.subtotal = this.precio * this.cantidad;

        return this.subtotal;
    }

    @Override
    public double obtenerTotalProducto() {
        double porcentajeImpuesto = 0.15;

        this.total = this.obtenerSubtotalProducto() * (1 + porcentajeImpuesto);

        return this.total;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}