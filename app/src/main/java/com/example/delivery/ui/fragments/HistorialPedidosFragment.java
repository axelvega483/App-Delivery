package com.example.delivery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.delivery.R;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.ui.adapters.AdapterPedidos;
import com.example.delivery.ui.adapters.AdapterPedidosHistorial;
import com.example.delivery.ui.viewmodel.PedidosViewModel;

import java.util.ArrayList;

public class HistorialPedidosFragment extends Fragment {

    private ListView listView;
    private TextView tvTitulo;
    PedidosViewModel pedidosViewModel;

    public HistorialPedidosFragment() {
        // Required empty public constructor
    }

    public static HistorialPedidosFragment newInstance() {
        HistorialPedidosFragment fragment = new HistorialPedidosFragment();
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
        View root = inflater.inflate(R.layout.fragment_historial_pedidos, container, false);
        init(root);
        setearDatos();
        return root;
    }

    private void setearDatos() {
        tvTitulo.setText("Historial de pedidos");
        pedidosViewModel.findAllEntregados().observe(getViewLifecycleOwner(), pedidos -> {
            AdapterPedidosHistorial adapter = new AdapterPedidosHistorial(getContext(), R.layout.item_list_pedido, (ArrayList<Pedido>) pedidos, pedidosViewModel);
            listView.setAdapter(adapter);
        });

    }

    private void init(View root) {
        tvTitulo = root.findViewById(R.id.tvTitulo);
        listView = root.findViewById(R.id.listViewHistorial);
        pedidosViewModel = new ViewModelProvider(requireActivity()).get(PedidosViewModel.class);

    }
}