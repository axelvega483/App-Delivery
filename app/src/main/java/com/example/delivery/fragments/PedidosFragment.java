package com.example.delivery.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.delivery.R;
import com.example.delivery.adapters.AdapterPedidos;
import com.example.delivery.model.Cliente;
import com.example.delivery.model.Direccion;
import com.example.delivery.model.Negocio;
import com.example.delivery.model.Pedido;
import com.example.delivery.model.Repartidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PedidosFragment extends Fragment {

    private ListView listView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PedidosFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static PedidosFragment newInstance(String param1, String param2) {
        PedidosFragment fragment = new PedidosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);
        listView=rootView.findViewById(R.id.listView);

        ArrayList<Pedido> pedidos=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pedidos.add(
                    new Pedido((long)i,
                            new Cliente("Cliente - "+i,"Apellido - "+i),
                            new Negocio((long)i*2,"Negocio - "+i*2),
                            new Repartidor("Exequiel","Raineri"),
                            new Date(),"Pendiente"));
        }

        AdapterPedidos adapterPedidos=new AdapterPedidos(getContext(),R.layout.item_list_pedido,pedidos);
        listView.setAdapter(adapterPedidos);
        return rootView;

    }

    public static PedidosFragment newInstance() {
        Bundle args = new Bundle();
        PedidosFragment fragment = new PedidosFragment();
        fragment.setArguments(args);
        return fragment;
    }
}