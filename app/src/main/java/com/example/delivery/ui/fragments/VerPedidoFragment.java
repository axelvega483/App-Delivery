package com.example.delivery.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.delivery.R;
import com.example.delivery.data.dao.PedidoDAO;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Pedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VerPedidoFragment extends Fragment {
    private TextView tvNroPedido, tvNombreNegocio, tvDireccionNegocio, tvNombreCliente, tvDireccionCliente, tvFechaPedido, tvEstadoPedido;
    private ListView listViewDetalle;
    private Button btnConfirmarPedido, btnVolver;
    private DatabaseApp db;
    private PedidoDAO pedidoDAO;

    private ArrayList<Pedido> pedido;

    private Context context;
    private String id;
    private String nombreNegocio;
    private String direccionNegocio;
    private String nombreCliente;
    private String direccionCliente;
    private String estado;

    public static VerPedidoFragment newInstance(String id,String negocio,String direccionNegocio ,String cliente,String direccionCliente, String estado) {
        VerPedidoFragment fragment = new VerPedidoFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("nombreNegocio", negocio);
        args.putString("direccionNegocio", direccionNegocio);
        args.putString("nombreCliente", cliente);
        args.putString("direccionCliente", direccionCliente);
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
            id = getArguments().getString("id");
            nombreNegocio = getArguments().getString("nombreNegocio");
            direccionNegocio = getArguments().getString("direccionNegocio");
            nombreCliente = getArguments().getString("nombreCliente");
            direccionCliente = getArguments().getString("direccionCliente");
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
        tvNroPedido.setText(id);
        tvFechaPedido.setText(sf.format(new Date()));
        tvEstadoPedido.setText(estado);
        tvNombreNegocio.setText(nombreNegocio);
        tvDireccionNegocio.setText(direccionNegocio);
        tvNombreCliente.setText(nombreCliente);
        tvDireccionCliente.setText(direccionCliente);

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
