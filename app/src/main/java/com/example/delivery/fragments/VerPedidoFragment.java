package com.example.delivery.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VerPedidoFragment extends Fragment {
    private TextView tvNroPedido, tvNombreNegocio, tvDireccionNegocio, tvNombreCliente, tvDireccionCliente, tvFechaPedido, tvEstadoPedido;
    private ListView listViewDetalle;
    private Button btnConfirmarPedido, btnVolver;

    private Context context;
    private String nombreNegocio;
    private String nombreCliente;
    private String estado;

    public static VerPedidoFragment newInstance(String negocio, String cliente, String estado) {
        VerPedidoFragment fragment = new VerPedidoFragment();
        Bundle args = new Bundle();
        args.putString("nombreNegocio", negocio);
        args.putString("nombreCliente", cliente);
        args.putString("estado", estado);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context; // Inicializar el contexto aquí
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_ver_pedido, container, false);
        if (getArguments() != null) {
            nombreNegocio = getArguments().getString("nombreNegocio");
            nombreCliente = getArguments().getString("nombreCliente");
            estado = getArguments().getString("estado");
        }
        init(rootView);
        setearDatos();
        initListener();
        return rootView;
    }

    private void initListener() {
        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, PedidoAceptadoFragment.newInstance())
                        .commit();
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

    private void setearDatos() {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        tvNroPedido.setText("Pedido #" + 33);
        tvFechaPedido.setText(sf.format(new Date()));
        tvEstadoPedido.setText(estado);
        tvNombreNegocio.setText(nombreNegocio);
        tvDireccionNegocio.setText("Belgrano 33");
        tvNombreCliente.setText(nombreCliente);
        tvDireccionCliente.setText("Quintana 32");

    }

    private void init(View rootView) {
        tvNroPedido = rootView.findViewById(R.id.tvNroPedido);
        tvFechaPedido = rootView.findViewById(R.id.tvFechaPedido);
        tvNombreNegocio = rootView.findViewById(R.id.tvNombreNegocio);
        tvDireccionNegocio = rootView.findViewById(R.id.tvDireccionNegocio);
        tvNombreCliente = rootView.findViewById(R.id.tvNombreCliente);
        tvDireccionCliente = rootView.findViewById(R.id.tvDireccionCliente);
        btnConfirmarPedido = rootView.findViewById(R.id.btnConfirmarPedido);
        btnVolver = rootView.findViewById(R.id.btnVolver);
        listViewDetalle = rootView.findViewById(R.id.listViewDetalle);
        tvEstadoPedido = rootView.findViewById(R.id.tvEstadoPedido);
    }
}
