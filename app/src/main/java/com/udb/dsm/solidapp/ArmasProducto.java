package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

/*
 *
 * Esta clase está orientada a los productos que sean de tipo arma, y en este ejemplo
 * ficticio se toma en cuenta que la compra de armas tiene un costo añadido  $55 para el traspaso
 * de armas, otro costo de $63 para la matrícula de arma y otro más de $3 para las pruebas
 * balísticas (las cuales no la poseen los demás productos) y tiene un impuesto del 25%
 *
 * */

public class ArmasProducto extends Producto {

    public ArmasProducto(String nombre, double precio) {
        super(nombre, precio);
    }

    /*
     *
     * Aquí vemos cómo la clase hija está redefiniendo los métodos obtenerSubtotalProducto y
     * obtenerTotalProducto para los productos de tipo arma, de modo que sea la misma clase hija
     * la encargada de definir la forma en la que se calcularán esos valores, para que no tengamos
     * que modificar el código de otras clases (por ejemplo la clase padre) cumpliendo así el
     * principio abierto/cerrado
     *
     * */

    @Override
    public double obtenerSubtotalProducto() {
        double costoTraspaso = 55;
        double costoMatricula = 63;
        double costoPruebaBalistica = 3;

        this.subtotal = (this.precio * this.cantidad) + costoTraspaso + costoMatricula + costoPruebaBalistica;

        return this.subtotal;
    }

    @Override
    public double obtenerTotalProducto() {
        double porcentajeImpuesto = 0.25;

        return this.obtenerSubtotalProducto() * (1 + porcentajeImpuesto);
    }

    @NonNull
    @Override
    public String toString() {
        return this.nombre;
    }
}
