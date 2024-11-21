package com.example.delivery;

import static com.example.delivery.R.*;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.delivery.ui.fragments.PedidosFragment;
import com.example.delivery.ui.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalActivity extends AppCompatActivity {
    private BottomNavigationView btnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.activity_home);
        init();
        initListener();
        openFragment(PedidosFragment.newInstance());
    }

    //INICIALIZA LOS COMPONENTES
    private void init() {
        btnNav = findViewById(id.btnNav);
    }

    //CAPTURA DE EVENTOS DE BOTONES
    private void initListener() {
        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == id.nav_principal) {
                    openFragment(PedidosFragment.newInstance());
                } else if (itemId == id.nav_verPedidos) {
                   /* fragment = VerPedidoFragment.newInstance( "Nombre del negocio", "Nombre del cliente");
                    openFragment(fragment);*/
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