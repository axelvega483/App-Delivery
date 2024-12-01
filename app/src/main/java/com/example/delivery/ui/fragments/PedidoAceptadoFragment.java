package com.example.delivery.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.R;
import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.ui.viewmodel.ClienteViewModel;
import com.example.delivery.ui.viewmodel.PedidoDetalleViewModel;
import com.example.delivery.ui.viewmodel.PedidosViewModel;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;
import com.google.android.gms.maps.MapView;

import java.util.concurrent.atomic.AtomicReference;

public class PedidoAceptadoFragment extends Fragment {
    private EditText edCodigoEntrega;
    private TextView tvNroPedido, tvNombreCliente, tvTotal;
    private Button btnConfirmarEntrega, btnVolver;
    private MapView mapViewPedido;
    private RepartidorViewModel repartidorViewModel;
    private PedidoDetalleViewModel pedidoDetalleViewModel;
    private ClienteViewModel clienteViewModel;


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
        // Inflar el layout
        View root = inflater.inflate(R.layout.fragment_pedido_aceptado, container, false);

        init(root);
        initListener();
        setearDatos();
        return root;
    }

    private void setearDatos() {
        // Actualizar datos del pedido
        repartidorViewModel.getPedidoActual().observe(getViewLifecycleOwner(), pedido -> {

            tvNroPedido.setText("Pedido #" + pedido.getId());

            // Obtener detalles del pedido
            pedidoDetalleViewModel.getDetalleByPedido(pedido.getId()).observe(getViewLifecycleOwner(), detalles -> {
                double total = 0.0;
                for (PedidoDetalle detalle : detalles) {
                    total += detalle.getSubtotal();
                }
                tvTotal.setText(String.format("$ %.2f", total));
            });

            // Obtener datos del cliente
            clienteViewModel.findById(pedido.getClienteId()).observe(getViewLifecycleOwner(), cliente -> {
                if (cliente != null) {
                    tvNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellido());
                } else {
                    tvNombreCliente.setText("Cliente no encontrado");
                }
            });
        });

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
        // Inicializar ViewModels
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider(requireActivity()).get(PedidoDetalleViewModel.class);
        clienteViewModel = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);

        edCodigoEntrega = root.findViewById(R.id.edCodigoEntrega);
        btnConfirmarEntrega = root.findViewById(R.id.btnConfirmarEntrega);
        btnVolver = root.findViewById(R.id.btnVolver);
        mapViewPedido = root.findViewById(R.id.mapViewPedido);
        tvNombreCliente = root.findViewById(R.id.tvNombreCliente);
        tvNroPedido = root.findViewById(R.id.tvNroPedido);
        tvTotal = root.findViewById(R.id.tvTotal);

    }
}