package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

/*
 *
 * Esta clase está orientada a los productos que sean de tipo comida, y en este ejemplo
 * ficticio se toma en cuenta que la compra de comida tiene un costo añadido de propina de $20 (la
 * cual no la poseen los demás productos) y tiene un impuesto del 13%
 *
 * */

public class ComidaProducto extends Producto {

    public ComidaProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    /*
     *
     * Aquí vemos cómo la clase hija está redefiniendo los métodos obtenerSubtotalProducto y
     * obtenerTotalProducto para los productos de tipo comida, de modo que sea la misma clase hija
     * la encargada de definir la forma en la que se calcularán esos valores, para que no tengamos
     * que modificar el código de otras clases (por ejemplo la clase padre), cumpliendo así el
     * principio abierto/cerrado
     *
     * */

    @Override
    public double obtenerSubtotalProducto() {
        double costoPropina = 20;

        this.subtotal = (this.precio * this.cantidad) + costoPropina;

        return Math.round(this.subtotal * 100.0) / 100.0;
    }

    @Override
    public double obtenerTotalProducto() {
        double porcentajeImpuesto = 0.13;

        this.total = this.obtenerSubtotalProducto() * (1 + porcentajeImpuesto);

        return Math.round(this.total * 100.0) / 100.0;
    }

    @NonNull
    @Override
    public String toString() {
        String texto = "- Producto: " + this.nombre + "\t\t\t - " +
                       "Cantidad: " + this.cantidad + "\n - " +
                       "Precio: $" + this.precio + "\t\t\t - " +
                       "Subtotal: $" + this.obtenerSubtotalProducto() + "\n - " +
                       "Total: $" + this.obtenerTotalProducto();

        return texto;
    }
}
