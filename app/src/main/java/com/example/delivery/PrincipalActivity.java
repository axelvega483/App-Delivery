package com.example.delivery;

import static com.example.delivery.R.*;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.delivery.SQLiteOpenHelper.AdminSQLiteOpenHelper;
import com.example.delivery.fragments.PedidosFragment;
import com.example.delivery.fragments.PerfilFragment;
import com.example.delivery.fragments.viewmodel.RepartidorSharedViewModel;
import com.example.delivery.model.Repartidor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrincipalActivity extends AppCompatActivity {
    private RepartidorSharedViewModel repartidorSharedViewModel;
    private BottomNavigationView btnNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        init();
        initListener();
        openFragment(PedidosFragment.newInstance());
    }

    //INICIALIZA LOS COMPONENTES
    private void init() {
        btnNav = findViewById(R.id.btnNav);

        AdminSQLiteOpenHelper db = new AdminSQLiteOpenHelper(this, "db", null, 1);
        Cursor cursor = db.obtenerRepartidor(Integer.parseInt(getIntent().getExtras().getString("idUsuario")));

        if (cursor != null && cursor.moveToFirst()) {
            Repartidor repartidor = new Repartidor();
            repartidor.setId((long) cursor.getLong(cursor.getColumnIndexOrThrow("id")));
            repartidor.setNombre(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            repartidor.setApellido(cursor.getString(cursor.getColumnIndexOrThrow("apellido")));
            repartidor.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            repartidor.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            repartidor.setDni(cursor.getString(cursor.getColumnIndexOrThrow("dni")));
            repartidor.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow("telefono")));

            repartidorSharedViewModel = new ViewModelProvider(this).get(RepartidorSharedViewModel.class);
            repartidorSharedViewModel.setRepartidor(repartidor);

            cursor.close();
        }
    }

    //CAPTURA DE EVENTOS DE BOTONES
    private void initListener() {
        btnNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_principal) {
                    openFragment(PedidosFragment.newInstance());
                } else if (itemId == R.id.nav_verPedidos) {
                   /* fragment = VerPedidoFragment.newInstance( "Nombre del negocio", "Nombre del cliente");
                    openFragment(fragment);*/
                } else if (itemId == R.id.nav_perfil) {
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