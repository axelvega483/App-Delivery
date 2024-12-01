package com.example.delivery;

import static com.example.delivery.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.delivery.data.model.Repartidor;
import com.example.delivery.ui.fragments.PedidoAceptadoFragment;
import com.example.delivery.ui.fragments.PedidosFragment;
import com.example.delivery.ui.fragments.PerfilFragment;
import com.example.delivery.ui.viewmodel.CategoriaViewModel;
import com.example.delivery.ui.viewmodel.ClienteViewModel;
import com.example.delivery.ui.viewmodel.NegocioViewModel;
import com.example.delivery.ui.viewmodel.PedidoDetalleViewModel;
import com.example.delivery.ui.viewmodel.PedidosViewModel;
import com.example.delivery.ui.viewmodel.ProductoViewModel;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;
import com.example.delivery.ui.viewmodel.SeguimientoViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalActivity extends AppCompatActivity {
    private BottomNavigationView btnNav;
    private RepartidorViewModel repartidorViewModel; // Instancia del ViewModel
<<<<<<< HEAD
    private boolean isPedidoActual;
=======
    private PedidosViewModel pedidosViewModel;
    private PedidoAceptadoFragment pedidoAceptadoFragment;
    private PedidoDetalleViewModel pedidoDetalleViewModel;
    private CategoriaViewModel categoriaViewModel;
    private ProductoViewModel productoViewModel;
    private NegocioViewModel negocioViewModel;
    private ClienteViewModel clienteViewModel;
    private SeguimientoViewModel seguimientoViewModel;
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_home);
        init(); // Inicializa componentes
        initListener(); // Configura el listener de navegación

        // Recibir los datos del Intent
        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String apellido = intent.getStringExtra("apellido");
        String direccion = intent.getStringExtra("direccion");
        String dni = intent.getStringExtra("dni");
        String telefono = intent.getStringExtra("telefono");
        String password = intent.getStringExtra("password");
        String email = intent.getStringExtra("email");

        // Crear el objeto Repartidor con los datos recibidos
        Repartidor repartidor = new Repartidor(nombre, apellido, direccion, dni, telefono, password, email);
        repartidorViewModel.setRepartidorLogueado(repartidor);
        Log.e("repartidorrrrrr: ", repartidor.toString());
        openFragment(PedidosFragment.newInstance());

    }

    // Inicializa los componentes
    private void init() {
        repartidorViewModel = new ViewModelProvider((PrincipalActivity.this)).get(RepartidorViewModel.class);
        pedidosViewModel = new ViewModelProvider((PrincipalActivity.this)).get(PedidosViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider((PrincipalActivity.this)).get(PedidoDetalleViewModel.class);
        categoriaViewModel = new ViewModelProvider((PrincipalActivity.this)).get(CategoriaViewModel.class);
        productoViewModel = new ViewModelProvider((PrincipalActivity.this)).get(ProductoViewModel.class);
        negocioViewModel = new ViewModelProvider((PrincipalActivity.this)).get(NegocioViewModel.class);
        clienteViewModel = new ViewModelProvider((PrincipalActivity.this)).get(ClienteViewModel.class);
        seguimientoViewModel = new ViewModelProvider((PrincipalActivity.this)).get(SeguimientoViewModel.class);
        btnNav = findViewById(id.btnNav);
        repartidorViewModel.getPedidoActual().observe(this, pedido -> {
            isPedidoActual = pedido != null ? true : false;
        });
    }

    // Captura de eventos de botones
    private void initListener() {
        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == id.nav_principal) {
                    openFragment(PedidosFragment.newInstance());
                } else if (itemId == id.nav_verPedidos) {
<<<<<<< HEAD
                    if (isPedidoActual) {
                        openFragment(PedidoAceptadoFragment.newInstance());
                    } else {
                        Toast.makeText(PrincipalActivity.this, "No hay pedido activo", Toast.LENGTH_SHORT).show();
=======
                    if(pedidosViewModel == null){
                        Toast.makeText(getApplicationContext(), "Por favor, seleccione un pedido.", Toast.LENGTH_SHORT).show();
                    }else{
                        openFragment(PedidoAceptadoFragment.newInstance());
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9
                    }

                } else if (itemId == id.nav_perfil) {
                    openFragment(PerfilFragment.newInstance());
                }
                return true;
            }
        });
    }

    public void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(id.frameContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}
