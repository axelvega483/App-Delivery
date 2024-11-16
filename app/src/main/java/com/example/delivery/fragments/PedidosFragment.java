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


    public PedidosFragment() {

    }

    public static PedidosFragment newInstance() {
        PedidosFragment fragment = new PedidosFragment();
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
        // Inflar el layout para este fragmento
        View rootView = inflater.inflate(R.layout.fragment_pedidos, container, false);
        listView = rootView.findViewById(R.id.listView);

        ArrayList<Pedido> pedidos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pedidos.add(
                    new Pedido((long) i,
                            new Cliente("Cliente - " + i, "Apellido - " + i),
                            new Negocio((long) i * 2, "Negocio - " + i * 2),
                            new Repartidor("Exequiel", "Raineri"),
                            new Date(), "Pendiente"));
        }

        AdapterPedidos adapterPedidos = new AdapterPedidos(getContext(), R.layout.item_list_pedido, pedidos);
        listView.setAdapter(adapterPedidos);
        return rootView;

    }

}