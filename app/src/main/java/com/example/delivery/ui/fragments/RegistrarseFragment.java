package com.example.delivery.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.delivery.R;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Repartidor;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistrarseFragment extends Fragment {
    private EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword, editTextDNI, editTextTelefono;
    private Button buttonRegistrar;
    private RepartidorViewModel repartidorViewModel;
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
        View root = inflater.inflate(R.layout.fragment_registrarse, container, false);
        init(root);
        initListener();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void init(View view) {
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        editTextNombre = view.findViewById(R.id.editTextNombre);
        editTextApellido = view.findViewById(R.id.editTextApellido);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextDNI = view.findViewById(R.id.editTextDNI);
        editTextTelefono = view.findViewById(R.id.editTextTelefono);
        buttonRegistrar = view.findViewById(R.id.buttonRegistrar);
    }

    private void initListener() {
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
        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty() || dni.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Repartidor repartidor = new Repartidor();
        repartidor.setNombre(nombre);
        repartidor.setApellido(apellido);
        repartidor.setEmail(email);
        repartidor.setPassword(password);
        repartidor.setTelefono(telefono);
        repartidor.setDni(dni);

        repartidorViewModel.save(repartidor);

        Toast.makeText(getActivity(), "Registro exitoso", Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                .commit();
    }
}
