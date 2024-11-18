package com.example.delivery.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.delivery.fragments.viewmodel.RepartidorSharedViewModel;
import com.example.delivery.model.Repartidor;

import java.util.Locale;

public class PerfilFragment extends Fragment {

    RepartidorSharedViewModel repartidorSharedViewModel;
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
        init(root);
        setearDatos();
        initListener();
        return root;
    }


    private void init(View root) {
        repartidorSharedViewModel = new ViewModelProvider(requireActivity()).get(RepartidorSharedViewModel.class);
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
        Repartidor repartidor = repartidorSharedViewModel.getRepartidor().getValue();
        tvNombreCompleto.setText(repartidor.getNombre().toUpperCase(Locale.ROOT) + " " + repartidor.getApellido().toUpperCase());
        edDireccion.setText(repartidor.getDireccion());
        edDni.setText(repartidor.getDni());
        edTelefono.setText(repartidor.getTelefono());
        edPassword.setText(repartidor.getPassword());
        edEmail.setText(repartidor.getEmail());
    }


    //METODO PARA CAPTURAR EVENTOS
    private void initListener() {
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                repartidorSharedViewModel.clearRepartidor();
                Intent intent = new Intent(getActivity(), MainActivity.class);
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