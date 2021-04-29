package com.udb.dsm.solidapp;

/*
*
* En el caso de esta aplicación de carrito de compras, tenemos el problema que cada tipo de producto
* puede tener atributos distintos para obtener su costo subtotal y total (por ejemplo: impuestos,
* costos añadidos, etc.), por lo que para poder cumplir con el principio abierto/cerrado, podemos
* separar la lógica de una sola clase Producto que trabaje la información de todos los tipos de
* producto a múltiples clases hijas de tipos de producto, que hereden variables y métodos de una
* clase padre Producto para poder calcular el subtotal y total de ese tipo específico de producto,
* de manera que en caso de que se aumenten los tipos de producto, no tengamos que modificar el
* código de la clase producto, sino que solamente tengamos que crear y trabajar directamente en las
* clases de los nuevos productos
*
* */

public abstract class Producto {
    public String nombre;
    public int cantidad;
    public double precio;

    public Producto() {}

    public Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = 0;
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

    public double obtenerSubtotalProducto() {
        return (this.precio * this.cantidad);
    };

    /*
     *
     * La clase padre asignará el método abstracto que será redefinido por cada clase hija de
     * producto dependiendo de los atributos y procedimientos que estas posean para calcular dicho
     * valor (total), de esta manera evitamos tener que modificar directamente el código de la clase
     * Product cada vez que se agreguen nuevos tipos de productos a la aplicación.
     *
     * */

    public abstract double obtenerTotalProducto();
}
