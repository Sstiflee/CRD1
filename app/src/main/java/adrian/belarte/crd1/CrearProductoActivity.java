package adrian.belarte.crd1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import adrian.belarte.crd1.databinding.ActivityCrearProductoBinding;

public class CrearProductoActivity extends AppCompatActivity {
    private ActivityCrearProductoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_crear_producto);
        binding = ActivityCrearProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAnyadirCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = nuevoProducto();

                if(producto != null){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("PRODUCTO",producto);
                    intent.putExtras(bundle);

                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(CrearProductoActivity.this, "FALTAN DATOS!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Producto nuevoProducto() {
        if(binding.txtNombreCrearProducto.getText().toString().isEmpty() ||
        binding.txtPrecioCrearProducto.getText().toString().isEmpty() ||
        binding.txtCantidadCrearProducto.getText().toString().isEmpty()){
            return null;
        }
        return new Producto(binding.txtNombreCrearProducto.getText().toString(),
                Integer.parseInt(binding.txtPrecioCrearProducto.getText().toString()),
                Integer.parseInt(binding.txtCantidadCrearProducto.getText().toString()));
    }
}