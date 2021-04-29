package com.udb.dsm.solidapp;

import androidx.annotation.NonNull;

/*
 *
 * Esta clase está orientada a los productos que sean de tipo arma, y en este ejemplo
 * ficticio se toma en cuenta que la compra de armas tiene un costo añadido  $55 para el traspaso
 * de armas, otro costo de $63 para la matrícula de arma y otro más de $3 para las pruebas
 * balísticas (las cuales no la poseen los demás productos) y tiene un impuesto del 25%
 *
 * A continuación extendemos de la clase padre Producto, para que de esa forma tengamos acceso al
 * método abstracto obtenerTotalProducto, para modificarlo a este tipo de producto
 *
 * */

public class ArmaProducto extends Producto {

    public ArmaProducto(String nombre, double precio) {
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

        double costoTraspaso = 55;
        double costoMatricula = 63;
        double costoPruebaBalistica = 3;
        double porcentajeImpuesto = 0.25;

        double sumaCostos = costoTraspaso + costoMatricula + costoPruebaBalistica;

        total = this.obtenerSubtotalProducto() * (1 + porcentajeImpuesto);
        total += sumaCostos;

        return Math.round(total * 100.0) / 100.0;
    }

    @NonNull
    @Override
    public String toString() { return this.nombre + " - $" + String.valueOf(this.precio); }
}
