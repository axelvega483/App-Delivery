package com.example.delivery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;
import com.example.delivery.SQLiteOpenHelper.AdminSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {
    EditText etCorreo, etPassword;
    Button btnIniciarSesion;
    SQLiteOpenHelper admin;
    Button btnRegistrarse;

    // Executor para manejar la tarea en segundo plano
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        admin = new AdminSQLiteOpenHelper(getActivity(), "db", null, 1);
        initListener();
    }

    //METODO PARA CONFIGURAR LOS EVENTOS DE BOTONES
    private void initListener() {
        btnIniciarSesion.setOnClickListener(v -> loginUsuario());

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, RegistrarseFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    //METODO PARA INICIALIZAR COMPONENTES
    private void init(View view) {
        etCorreo = view.findViewById(R.id.etCorreo);
        etPassword = view.findViewById(R.id.etPassword);
        btnIniciarSesion = view.findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = view.findViewById(R.id.btnRegistrarse);
    }

    private void loginUsuario() {
        // Obtener los datos de los EditText
        String email = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        // Validar si los campos no están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, complete ambos campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar la tarea de login en un hilo de fondo
        executorService.execute(() -> {
            SQLiteDatabase db = admin.getReadableDatabase();

            // Consultar si existe un usuario con el email y password ingresados
            String[] columns = {"id", "nombre", "apellido", "email", "password"};
            String selection = "email = ? AND password = ?";
            String[] selectionArgs = {email, password};

            Cursor cursor = db.query("repartidores", columns, selection, selectionArgs,
                    null, null, null);

            boolean usuarioEncontrado = cursor != null && cursor.moveToFirst();

            // Usar un Handler para actualizar la UI en el hilo principal
            new Handler(Looper.getMainLooper()).post(() -> {
                if (usuarioEncontrado) {
                    // Login exitoso
                    Toast.makeText(getActivity(), "Login exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                    intent.putExtra("idUsuario", cursor.getString(0));
                    startActivity(intent);

                } else {
                    // Error en las credenciales
                    Toast.makeText(getActivity(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }

                // Cerrar el cursor y la base de datos
                if (cursor != null) cursor.close();
                db.close();
            });
        });
    }
}
