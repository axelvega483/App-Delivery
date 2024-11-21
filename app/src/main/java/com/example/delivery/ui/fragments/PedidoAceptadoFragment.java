package com.example.delivery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.delivery.R;
import com.google.android.gms.maps.MapView;

public class PedidoAceptadoFragment extends Fragment {
    private EditText edCodigoEntrega;
    private Button btnConfirmarEntrega, btnVolver;
    private MapView mapViewPedido;

    public PedidoAceptadoFragment() {
        // Required empty public constructor
    }


    public static PedidoAceptadoFragment newInstance() {
        Bundle args = new Bundle();
        PedidoAceptadoFragment fragment = new PedidoAceptadoFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pedido_aceptado, container, false);
        init(root);
        initListener();
        return root;
    }

    private void initListener() {
        btnConfirmarEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Entrega Confirmada", Toast.LENGTH_SHORT).show();
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, PedidosFragment.newInstance())
                        .commit();
            }
        });
    }

    private void init(View root) {
        edCodigoEntrega = root.findViewById(R.id.edCodigoEntrega);
        btnConfirmarEntrega = root.findViewById(R.id.btnConfirmarEntrega);
        btnVolver = root.findViewById(R.id.btnVolver);
        mapViewPedido = root.findViewById(R.id.mapViewPedido);
    }
}