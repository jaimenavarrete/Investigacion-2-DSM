package com.udb.dsm.solidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
*
* El principio abierto/cerrado (Opened/Close, segundo principio SOLID) nos dice que que una clase
* debe estar abierta a extensión pero cerrada a modificaciones. Esto lo que viene a decir es que
* nuestra aplicación debería estar diseñada para que ante peticiones de cambio en la aplicación
* (por ejemplo, se solicite que cumpla otros requerimientos de necesidades que se han encontrado),
* deberíamos ser capaces de añadir funcionalidad sin modificar la existente (siempre que sea posible).
*
* */

public class MainActivity extends AppCompatActivity {
    Spinner spinner;

    TextView textViewSubtotal, textViewTotal;
    EditText editTextCantidad;
    Button btnVerCostos, btnAgregar;

    ListView listViewProductosCarrito;
    ArrayList<Producto> listaProductosCarrito;
    ArrayList<String> textoListaProductosCarrito;
    ArrayAdapter<String> arrayAdapterProductosCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declararElementos();
        inicializarElementos();
        llenarSpinner();
    }

    protected void declararElementos() {
        spinner = findViewById(R.id.spinnerProductos);

        editTextCantidad = findViewById(R.id.editTextCantidad);
        textViewSubtotal = findViewById(R.id.textViewSubtotal);
        textViewTotal = findViewById(R.id.textViewTotal);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnVerCostos = findViewById(R.id.btnVerCostos);

        listViewProductosCarrito = findViewById(R.id.listViewProductosCarrito);
        listaProductosCarrito = new ArrayList<>();
        textoListaProductosCarrito = new ArrayList<>();
        arrayAdapterProductosCarrito = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, textoListaProductosCarrito);
    }

    protected void inicializarElementos() {
        //calcularCostosCarrito();

        btnAgregar.setOnClickListener(v -> {
            agregarProductoCarrito();
            calcularCostosCarrito();
        });

        btnVerCostos.setOnClickListener(v -> {
            Intent intent = new Intent(this, VerCostosActivity.class);
            startActivity(intent);
        });

        listViewProductosCarrito.setAdapter(arrayAdapterProductosCarrito);
        listViewProductosCarrito.setOnItemLongClickListener(eventoEliminarProducto());
    }

    protected AdapterView.OnItemLongClickListener eventoEliminarProducto() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setMessage("¿Está seguro de eliminar este producto del carrito?").setTitle("Confirmación");

                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarProductoCarrito(position);
                        calcularCostosCarrito();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

                builder.create().show();

                return true;
            }
        };
    }

    /*
     *
     * En este método podemos observar fácilmente las ventajas del principio abierto/cerrado, ya que
     * a pesar de tener tres tipos de productos que poseen atributos distintos para calcular el
     * subtotal y el total, no necesitamos hacer modificaciones de código para cada uno de ellos,
     * sino que solamente debemos hacer uso de los métodos abstractos de la clase padre. De manera
     * que si más adelante tenemos que agregar otros tipos de productos, no necesitaremos modificar
     * este código.
     *
     * */

    protected void calcularCostosCarrito() {
        double subtotalCarrito = 0;
        double totalCarrito = 0;

        for(Producto producto : listaProductosCarrito) {
            subtotalCarrito += producto.obtenerSubtotalProducto();
            totalCarrito += producto.obtenerTotalProducto();
        }

        mostrarCostosCarrito(subtotalCarrito, totalCarrito);
    }

    protected void mostrarCostosCarrito(double subtotalCarrito, double totalCarrito) {
        textViewSubtotal.setText("SUBTOTAL: $" + subtotalCarrito);
        textViewTotal.setText("TOTAL: $" + totalCarrito);
    }

    protected void llenarSpinner() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new ComidaProducto("Comida 1", 12.5));
        productos.add(new ComidaProducto("Comida 2", 25.00));
        productos.add(new ComidaProducto("Comida 3", 45.70));
        productos.add(new BebidaAlcoholicaProducto("Bebida alcohólica 1", 14.50));
        productos.add(new BebidaAlcoholicaProducto("Bebida alcohólica 2", 75.10));
        productos.add(new BebidaAlcoholicaProducto("Bebida alcohólica 3", 60.00));
        productos.add(new ArmaProducto("Arma 1", 125.00));
        productos.add(new ArmaProducto("Arma 2", 255.00));
        productos.add(new ArmaProducto("Arma 3", 450.00));

        ArrayAdapter<Producto> adaptador = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, productos);

        spinner.setAdapter(adaptador);
    }

    protected void agregarProductoCarrito() {
        if(!editTextCantidad.getText().toString().equals("") && Integer.parseInt(editTextCantidad.getText().toString()) > 0) {
            Producto nuevoProducto = (Producto)spinner.getSelectedItem();
            nuevoProducto.setCantidad(Integer.parseInt(editTextCantidad.getText().toString()));

            listaProductosCarrito.add(nuevoProducto);
            textoListaProductosCarrito.add(
                "- Producto: " + nuevoProducto.nombre + " " +
                "- Cantidad: " + nuevoProducto.cantidad + "\n" +
                "- Precio: $" + nuevoProducto.precio + " " +
                "- Subtotal: $" + nuevoProducto.obtenerSubtotalProducto() + "\n" +
                "- Total: $" + nuevoProducto.obtenerTotalProducto()
            );
            arrayAdapterProductosCarrito.notifyDataSetChanged();

            Toast.makeText(this, "El producto ha sido agregado correctamente al carrito", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Debe colocar la cantidad a agregar y debe ser mayor a 0" , Toast.LENGTH_LONG).show();
        }
    }

    protected void eliminarProductoCarrito(int position) {
        try {
            listaProductosCarrito.remove(position);
            textoListaProductosCarrito.remove(position);
            arrayAdapterProductosCarrito.notifyDataSetChanged();

            Toast.makeText(MainActivity.this, "El producto ha sido eliminado correctamente del carrito", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "Hubo un error al eliminar el producto del carrito", Toast.LENGTH_LONG).show();
        }
    }
}