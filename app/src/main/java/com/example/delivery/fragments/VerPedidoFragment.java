package com.example.delivery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.delivery.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerPedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerPedidoFragment extends Fragment {
    private String nombreNegocio;
    private String nombreCliente;
    private String estado;

    public static VerPedidoFragment newInstance(String negocio, String cliente,String estado) {
        VerPedidoFragment fragment = new VerPedidoFragment();
        Bundle args = new Bundle();
        args.putString("nombreNegocio", negocio);
        args.putString("nombreCliente", cliente);
        args.putString("estado",estado);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nombreNegocio = getArguments().getString("nombreNegocio");
            nombreCliente = getArguments().getString("nombreCliente");
            estado=getArguments().getString("estado");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_ver_pedido, container, false);

        // Encontrar las vistas y asignar los datos
        TextView tvNombreNegocio = rootView.findViewById(R.id.tvNombreNegocioDetalle);
        TextView tvNombreCliente = rootView.findViewById(R.id.tvNombreClienteDetalle);
        TextView tvEstadoDetalle= rootView.findViewById(R.id.tvEstadoDetalle);

        // Asignar los datos a los TextViews
        tvNombreNegocio.setText(nombreNegocio);
        tvNombreCliente.setText(nombreCliente);
        tvEstadoDetalle.setText(estado);

        return rootView;
    }
}
