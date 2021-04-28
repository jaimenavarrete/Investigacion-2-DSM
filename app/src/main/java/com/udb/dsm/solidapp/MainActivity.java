package com.udb.dsm.solidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    Button btnAgregar;

    ListView listViewProductosCarrito;
    ArrayList<Producto> listaProductosCarrito;
    ArrayAdapter<Producto> arrayAdapterProductosCarrito;

    double subtotalCarrito = 0.0,
           totalCarrito = 0.0;

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

        listViewProductosCarrito = findViewById(R.id.listViewProductosCarrito);
        listaProductosCarrito = new ArrayList<>();
        arrayAdapterProductosCarrito = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaProductosCarrito);
    }

    protected void inicializarElementos() {
        mostrarCostosCarrito();

        btnAgregar.setOnClickListener(v -> {
            agregarProductoCarrito();
        });

        listViewProductosCarrito.setAdapter(arrayAdapterProductosCarrito);
        listViewProductosCarrito.setOnItemLongClickListener(eventoEliminarProducto());
    }

    /*
     *
     * En este método podemos observar fácilmente las ventajas del principio abierto/cerrado, ya que
     * a pesar de tener tres tipos de productos que poseen atributos distintos para calcular el
     * subtotal y el total, no necesitamos hacer modificaciones de código para cada uno de ellos,
     * sino que solamente debemos hacer uso de los métodos abstractos de la clase padre. De manera
     * que si más adelante tenemos que agregar otros tipos de productos, no necesitaremos modificar
     * ni siquiera tocar este código.
     *
     * */

    protected void calcularCostosCarrito() {
        subtotalCarrito = 0;
        totalCarrito = 0;

        for(Producto producto : listaProductosCarrito) {
            subtotalCarrito += producto.obtenerSubtotalProducto();
            totalCarrito += producto.obtenerTotalProducto();
        }

        mostrarCostosCarrito();
    }

    protected void mostrarCostosCarrito() {
        textViewSubtotal.setText("SUBTOTAL: $" + subtotalCarrito);
        textViewTotal.setText("TOTAL: $" + totalCarrito);
    }

    protected void llenarSpinner() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new ComidaProducto("Pizza", 1.5));
        productos.add(new BebidaAlcoholicaProducto("Leche", 2.5));

        ArrayAdapter<Producto> adaptador = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, productos);

        spinner.setAdapter(adaptador);
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

    protected void agregarProductoCarrito() {
        if(!editTextCantidad.getText().toString().equals("")) {
            Producto producto = (Producto)spinner.getSelectedItem();
            producto.setCantidad(Integer.parseInt(editTextCantidad.getText().toString()));

            listaProductosCarrito.add(producto);
            arrayAdapterProductosCarrito.notifyDataSetChanged();
            calcularCostosCarrito();

            Toast.makeText(this, "El producto ha sido agregado correctamente al carrito", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Debe colocar la cantidad a agregar" , Toast.LENGTH_LONG).show();
        }
    }

    protected void eliminarProductoCarrito(int position) {
        try {
            listaProductosCarrito.remove(position);
            arrayAdapterProductosCarrito.notifyDataSetChanged();
            calcularCostosCarrito();

            Toast.makeText(MainActivity.this, "El producto ha sido eliminado correctamente del carrito", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this, "Hubo un error al eliminar el producto del carrito", Toast.LENGTH_LONG).show();
        }
    }
}