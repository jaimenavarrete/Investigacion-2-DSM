package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

/*
 *
 * Esta clase está orientada a los productos que sean de tipo comida, y en este ejemplo
 * ficticio se toma en cuenta que la compra de comida tiene un costo añadido de propina de $20 (la
 * cual no la poseen los demás productos) y tiene un impuesto del 13%
 *
 * A continuación extendemos de la clase padre Producto, para que de esa forma tengamos acceso al
 * método abstracto obtenerTotalProducto, para modificarlo a este tipo de producto
 *
 * */

public class ComidaProducto extends Producto {

    public ComidaProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    /*
     *
     * Aquí vemos cómo la clase hija está redefiniendo el método obtenerTotalProducto para los
     * productos de tipo comida, de modo que sea la misma clase hija la encargada de definir la
     * forma en la que se calculará ese valor, para que no tengamos que modificar el código de otras
     * clases (por ejemplo la clase padre), cumpliendo así el principio abierto/cerrado
     *
     * */

    @Override
    public double obtenerTotalProducto() {
        double total;

        double costoPropina = 20;
        double porcentajeImpuesto = 0.13;

        total = this.obtenerSubtotalProducto() * (1 + porcentajeImpuesto);
        total += costoPropina;

        return Math.round(total * 100.0) / 100.0;
    }

    @NonNull
    @Override
    public String toString() { return this.nombre + " - $" + String.valueOf(this.precio); }
}
