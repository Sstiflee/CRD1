package adrian.belarte.crd1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.core.view.WindowCompat;

import adrian.belarte.crd1.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> laucherProducto;
    private ArrayList<Producto> listaProductos;
    private int cantidadTotal;
    private Float precioTotal;
    private int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listaProductos = new ArrayList<>();
        iniciarLauchers();


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                laucherProducto.launch(new Intent(MainActivity.this, CrearProductoActivity.class));
            }
        });
    }

    private void iniciarLauchers() {
        laucherProducto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == RESULT_OK){
                            if(o.getData() != null && o.getData().getExtras() != null){
                                Producto producto = (Producto) o.getData().getExtras().getSerializable("PRODUCTO");
                                listaProductos.add(producto);
                                mostrarProductos();
                            }else{
                                Toast.makeText(MainActivity.this, "ACCION CANCELADA", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    private void mostrarProductos() {

        binding.contentMain.contenido.removeAllViews();
        cantidadTotal = 0;
        precioTotal = (float) 0;
        for (int i = 0; i < listaProductos.size(); i++) {
            Producto producto = listaProductos.get(i);

            cantidadTotal += listaProductos.get(i).getCantidad();
            binding.contentMain.txtCantidadTotalMain.setText(String.valueOf(cantidadTotal));

            precioTotal += (float) (listaProductos.get(i).getCantidad() * listaProductos.get(i).getPrecio());
            binding.contentMain.txtPrecioTotalMain.setText(String.valueOf(precioTotal));

            View productoView =
                    LayoutInflater.from(MainActivity.this).inflate(R.layout.producto_model_view,null);

            TextView lbnombre = productoView.findViewById(R.id.txtNombreModelView);
            TextView lbPrecio = productoView.findViewById(R.id.txtPrecioModelView);
            TextView lbCantidad = productoView.findViewById(R.id.txtCantidadModelView);

            lbnombre.setText(producto.getNombre());
            lbPrecio.setText(String.valueOf(producto.getPrecio()));
            lbCantidad.setText(String.valueOf(producto.getCantidad()));


            int finalI = i;
            productoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listaProductos.remove(finalI);
                }
            });

            binding.contentMain.contenido.addView(productoView);
        }

    }

}