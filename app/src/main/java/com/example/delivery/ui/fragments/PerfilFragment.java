package com.example.delivery.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.delivery.MainActivity;
import com.example.delivery.R;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;
import java.util.Locale;

public class PerfilFragment extends Fragment {

    private RepartidorViewModel repartidorViewModel;
    private EditText edEmail, edPassword, edDireccion, edDni, edTelefono;
    private TextView tvNombreCompleto;
    private Button btnCerrarSesion, btnHistorialPedidos;

    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        Log.d("PerfilFragment", "onCreateView: Fragment creado");
        init(root);
        initListener(); // Inicializa los listeners aquí
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        setearDatos(); // Llama a setearDatos cuando el fragmento está en estado STARTED
    }

    private void init(View root) {
        edDni = root.findViewById(R.id.etDni);
        edTelefono = root.findViewById(R.id.edTelefono);
        edPassword = root.findViewById(R.id.edPassword);
        edEmail = root.findViewById(R.id.edEmail);
        tvNombreCompleto = root.findViewById(R.id.tvNombreCompleto);
        edDireccion = root.findViewById(R.id.edDireccion);
        btnCerrarSesion = root.findViewById(R.id.btnCerrarSesion);
        btnHistorialPedidos = root.findViewById(R.id.btnHistorialPedidos);
    }

    private void setearDatos() {
        Log.e("setearDatos", "seteando datos");

        if (repartidorViewModel.getRepartidorLogueado().getValue() == null) {
            Log.e("Repartidor", "Repartidor aún no disponible");
            // Puedes mostrar un mensaje o tomar otra acción
        }

        repartidorViewModel.getRepartidorLogueado().observe(getViewLifecycleOwner(), repartidor -> {
            if (repartidor != null) {
                Log.e("Repartidor", "Repartidor encontrado: " + repartidor.getNombre());



                tvNombreCompleto.setText(repartidor.getNombre().toUpperCase(Locale.ROOT) + " " + repartidor.getApellido().toUpperCase());
                edDireccion.setText(repartidor.getDireccion());
                edDni.setText(repartidor.getDni());
                edTelefono.setText(repartidor.getTelefono());
                edPassword.setText(repartidor.getPassword());
                edEmail.setText(repartidor.getEmail());
            } else {
                Log.e("Repartidor", "Repartidor no encontrado.");
            }
        });

        Log.e("ssss", repartidorViewModel.toString());
    }

    // Método para capturar eventos
    private void initListener() {
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                repartidorViewModel.setRepartidorLogueado(null);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnHistorialPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Ver historial de pedidos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}