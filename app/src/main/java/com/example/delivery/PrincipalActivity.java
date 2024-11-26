package com.example.delivery;

import static com.example.delivery.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.delivery.data.model.Repartidor;
import com.example.delivery.ui.fragments.PedidosFragment;
import com.example.delivery.ui.fragments.PerfilFragment;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalActivity extends AppCompatActivity {
    private BottomNavigationView btnNav;
    private RepartidorViewModel repartidorViewModel; // Instancia del ViewModel

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
        Log.e("repartidorrrrrr: ",repartidor.toString());
        openFragment(PedidosFragment.newInstance());
    }

    // Inicializa los componentes
    private void init() {
        repartidorViewModel = new ViewModelProvider(this).get(RepartidorViewModel.class);
        btnNav = findViewById(id.btnNav);
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
                    // Aquí puedes añadir otro fragmento si es necesario
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
