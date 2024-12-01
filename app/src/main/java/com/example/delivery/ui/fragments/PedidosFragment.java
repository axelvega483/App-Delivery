package com.example.delivery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.delivery.R;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.ui.adapters.AdapterPedidos;
import com.example.delivery.ui.viewmodel.PedidosViewModel;

import java.util.ArrayList;
import java.util.concurrent.Executors;

public class PedidosFragment extends Fragment {

    private ListView listView;
    private AdapterPedidos adapter;
    PedidosViewModel pedidosViewModel;

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
        init(rootView);
        setearDatos();
        return rootView;

    }

    private void setearDatos() {
        pedidosViewModel.findAllPendiente().observe(getViewLifecycleOwner(), pedidos -> {
           adapter= new AdapterPedidos(getContext(), R.layout.item_list_pedido, (ArrayList<Pedido>) pedidos, pedidosViewModel);
           listView.setAdapter(adapter);
        });
    }

    private void init(View rootView) {
        listView = rootView.findViewById(R.id.listView);
        pedidosViewModel = new ViewModelProvider(requireActivity()).get(PedidosViewModel.class);


    }


}