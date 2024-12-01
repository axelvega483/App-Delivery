package com.example.delivery.ui.fragments;

import android.content.Intent;
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
import com.example.delivery.data.model.Pedido;
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
<<<<<<< HEAD
=======
    private PedidosViewModel pedidosViewModel;
    private Long pedidoId;
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9


    public static PedidoAceptadoFragment newInstance() {
        Bundle args = new Bundle();
        PedidoAceptadoFragment fragment = new PedidoAceptadoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout
        View root = inflater.inflate(R.layout.fragment_pedido_aceptado, container, false);

=======
        Log.e("PEDIDOACEPTADO-ONCREATE", "CREANDO FRAGMENTO");

        // Asegúrate de inicializar el ViewModel aquí
        pedidosViewModel = new ViewModelProvider(requireActivity()).get(PedidosViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pedido_aceptado, container, false);

        // Solo inicializamos si no se ha hecho ya
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9
        init(root);
        initListener();
        setearDatos();
        return root;
    }

    private void setearDatos() {
<<<<<<< HEAD
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

=======
        Pedido pedido = pedidosViewModel.getPedidoActual().getValue();
        if (pedido != null) {
            tvNroPedido.setText(pedido.getId().toString());
            // Resto de la lógica
        } else {
            // Si no, obténlo desde la base de datos
            if (getArguments() != null) {
                pedidoId = getArguments().getLong("pedidoId");
                Log.e("PEDDDDDIDO", pedidoId.toString());
                pedidosViewModel.findById(pedidoId).observe(requireActivity(), pedidoFromDb -> {
                    if (pedidoFromDb != null) {
                        pedidosViewModel.setPedido(pedidoFromDb);
                        tvNroPedido.setText(pedidoFromDb.getId().toString());
                    }
                });
            }
        }
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, PedidosFragment.newInstance()).commit();
            }
        });
    }

    private void init(View root) {
<<<<<<< HEAD
        // Inicializar ViewModels
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider(requireActivity()).get(PedidoDetalleViewModel.class);
        clienteViewModel = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);

=======
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider(requireActivity()).get(PedidoDetalleViewModel.class);
        clienteViewModel = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
>>>>>>> f42bb743f8814cf6ebf96134ddd082d36c63a5a9
        edCodigoEntrega = root.findViewById(R.id.edCodigoEntrega);
        btnConfirmarEntrega = root.findViewById(R.id.btnConfirmarEntrega);
        btnVolver = root.findViewById(R.id.btnVolver);
        mapViewPedido = root.findViewById(R.id.mapViewPedido);
        tvNombreCliente = root.findViewById(R.id.tvNombreCliente);
        tvNroPedido = root.findViewById(R.id.tvNroPedido);
        tvTotal = root.findViewById(R.id.tvTotal);
    }
}