package com.example.delivery.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;


public class LoginFragment extends Fragment {
    private EditText etCorreo, etPassword;
    private Button btnIniciarSesion, btnRegistrarse;
    private RepartidorViewModel repartidorViewModel;

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
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        // Inicializar el ExecutorService solo una vez
        init(root);
        initListener();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //METODO PARA CONFIGURAR LOS EVENTOS DE BOTONES
    private void initListener() {
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        btnIniciarSesion.setOnClickListener(v -> loginUsuario());

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, RegistrarseFragment.newInstance())
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

        repartidorViewModel.findByEmailAndPassword(email, password).observe(requireActivity(), repartidor -> {
            if (repartidor == null) {
                Toast.makeText(getActivity(), "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            } else {
                repartidorViewModel.setRepartidorLogueado(repartidor);
                Toast.makeText(getActivity(), "Bienvenido " + repartidor.getNombre(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PrincipalActivity.class);
                startActivity(intent);
            }
        });


    }
}
