package com.example.delivery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.delivery.R;

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
        init(rootView);
        setearDatos();
        return rootView;

    }

    private void setearDatos() {

    }

    private void init(View rootView) {
        listView = rootView.findViewById(R.id.listView);
    }


}