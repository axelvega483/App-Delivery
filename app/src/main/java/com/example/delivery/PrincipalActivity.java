package com.example.delivery;

import static androidx.fragment.app.FragmentManagerKt.commit;
import static com.example.delivery.R.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.delivery.fragments.PedidosFragment;
import com.example.delivery.fragments.PerfilFragment;
import com.example.delivery.fragments.VerPedidoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.example.delivery.R;

public class PrincipalActivity extends AppCompatActivity {

    private BottomNavigationView btnNav;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        btnNav = findViewById(R.id.btnNav);
        fragmentManager = getSupportFragmentManager();
        openFragment(PedidosFragment.newInstance());
        initListener();
    }


    private void initListener(){
        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_principal) {
                    fragment = PedidosFragment.newInstance();
                    openFragment(fragment);
                } else if (itemId == R.id.nav_verPedidos) {
                   /* fragment = VerPedidoFragment.newInstance( "Nombre del negocio", "Nombre del cliente");
                    openFragment(fragment);*/
                } else if (itemId == R.id.nav_perfil) {
                    fragment = PerfilFragment.newInstance();
                    openFragment(fragment);
                }
                return true;
            }
        });
    }


    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(id.frameContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}