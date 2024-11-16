package com.example.delivery.fragments;

import android.content.ContentValues;
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

import com.example.delivery.R;
import com.example.delivery.SQLiteOpenHelper.AdminSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrarseFragment extends Fragment {
    EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword, editTextDNI, editTextTelefono;
    Button buttonRegistrar;
    SQLiteOpenHelper admin;

    // Executor para manejar la tarea en segundo plano
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public RegistrarseFragment() {
        // Required empty public constructor
    }

    public static RegistrarseFragment newInstance() {
        RegistrarseFragment fragment = new RegistrarseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrarse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido = view.findViewById(R.id.editTextApellido);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextDNI = view.findViewById(R.id.editTextDNI);
        editTextTelefono = view.findViewById(R.id.editTextTelefono);
        buttonRegistrar = view.findViewById(R.id.buttonRegistrar);
        admin = new AdminSQLiteOpenHelper(getActivity(), "db", null, 1);

        // Configurar el click listener para el botón de registrar
        buttonRegistrar.setOnClickListener(v -> registrarRepartidor());
    }

    private void registrarRepartidor() {
        // Obtener datos de los EditText
        String nombre = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String dni = editTextDNI.getText().toString();
        String telefono = editTextTelefono.getText().toString();

        // Validar si los campos no están vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()  || password.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar la tarea de registro en un hilo de fondo
        executorService.execute(() -> {
            SQLiteDatabase db = admin.getWritableDatabase();

            // Crear un objeto ContentValues para insertar los datos
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("email", email);
            values.put("password", password);
            values.put("dni", dni);
            values.put("telefono", telefono);

            // Insertar los datos en la base de datos
            long id = db.insert("repartidores", null, values);

            // Usar un Handler para actualizar la UI en el hilo principal
            new Handler(Looper.getMainLooper()).post(() -> {
                // Verificar si la inserción fue exitosa
                if (id == -1) {
                    Toast.makeText(getActivity(), "Error al registrar el repartidor", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Repartidor registrado correctamente", Toast.LENGTH_SHORT).show();
                    // Regresar al fragmento anterior
                    getFragmentManager().popBackStack();
                }

                // Cerrar la base de datos
                db.close();
            });
        });
    }
}
