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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;

    EditText editTextCantidad;
    Button btnAgregar;

    ListView listViewProductosCarrito;
    ArrayList<Producto> listaProductosCarrito;
    ArrayAdapter<Producto> arrayAdapterProductosCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarElementos();
        llenarSpinner();
    }

    protected void inicializarElementos() {
        spinner = findViewById(R.id.spinnerProductos);

        editTextCantidad = findViewById(R.id.editTextCantidad);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(v -> {
            agregarProductoCarrito();
        });

        listViewProductosCarrito = findViewById(R.id.listViewProductosCarrito);
        listaProductosCarrito = new ArrayList<>();

        arrayAdapterProductosCarrito = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listaProductosCarrito);
        listViewProductosCarrito.setAdapter(arrayAdapterProductosCarrito);

        listViewProductosCarrito.setOnItemLongClickListener(eventoEliminarProducto());
    }

    protected void llenarSpinner() {
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(new ComidaProducto("Pizza", 1.5));
        productos.add(new BebidaProducto("Leche", 2.5));

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
                        try {
                            listaProductosCarrito.remove(position);
                            Toast.makeText(MainActivity.this, "El producto ha sido eliminado correctamente del carrito", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "Hubo un error al eliminar el producto del carrito", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        };
    }

    protected void agregarProductoCarrito() {
        if(!editTextCantidad.getText().equals("")) {
            Producto producto = (Producto)spinner.getSelectedItem();
            producto.setCantidad(Integer.parseInt(editTextCantidad.getText().toString()));

            listaProductosCarrito.add(producto);

            Toast.makeText(this, "El producto ha sido agregado correctamente al carrito", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Debe colocar la cantidad a agregar" , Toast.LENGTH_LONG).show();
        }
    }
}