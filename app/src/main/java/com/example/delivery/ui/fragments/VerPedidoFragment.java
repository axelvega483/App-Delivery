package com.example.delivery.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.security.ConfirmationPrompt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;
import com.example.delivery.data.database.DatabaseApp;
import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Negocio;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.ui.adapters.AdapterPedidoDetalle;
import com.example.delivery.ui.viewmodel.ClienteViewModel;
import com.example.delivery.ui.viewmodel.PedidoDetalleViewModel;
import com.example.delivery.ui.viewmodel.PedidosViewModel;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;

public class VerPedidoFragment extends Fragment {
    private TextView tvNroPedido, tvNombreNegocio, tvDireccionNegocio, tvNombreCliente, tvDireccionCliente, tvFechaPedido, tvEstadoPedido;
    private ListView listViewDetalle;
    private Button btnConfirmarPedido, btnVolver;

    private AdapterPedidoDetalle adapter;
    private ArrayList<PedidoDetalle> pedidoDetalles;
    private DatabaseApp db;
    private Context context;
    private String id;
    //private String nombreNegocio;
    //private String direccionNegocio;
    //private String nombreCliente;
    //private String direccionCliente;
    //private String estado;
    private Pedido pedido;
    private Negocio negocio;
    private Cliente cliente;
    PedidoDetalleViewModel pedidoDetalleViewModel;
    PedidosViewModel pedidosViewModel;
    RepartidorViewModel repartidorViewModel;
    ClienteViewModel clienteViewModel;

    public static VerPedidoFragment newInstance(String id, String negocio, String direccionNegocio, String cliente, String direccionCliente, String estado) {
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
        init(rootView);
        if (getArguments() != null) {
            id = getArguments().getString("id");
            db.pedidoDAO().findById(Long.valueOf(id)).observe(getViewLifecycleOwner(), pedido -> {
                this.pedido = pedido;

                if (pedido != null) {
                    db.negocioDAO().findById(pedido.getNegocioId()).observe(getViewLifecycleOwner(), negocio -> {
                        this.negocio = negocio;
                        setearDatos();
                    });
                    db.clienteDAO().findById(pedido.getClienteId()).observe(getViewLifecycleOwner(), cliente -> {
                        this.cliente = cliente;
                        setearDatos();
                    });

                }
            });
        }


        initListener();
        return rootView;
    }

    private void initListener() {
        btnConfirmarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                alert.setTitle("Tomar este pedido?");
                alert.setMessage("¿Estas seguro de tomar este pedido?");
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tomarPedido();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }


                });
                alert.show();
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

    private void tomarPedido() {

        if (pedido!=null){
            pedido.setEstado("ACTIVO");
            pedidosViewModel.update(pedido);
            Toast.makeText(getContext(), "Se tomó un pedido", Toast.LENGTH_SHORT).show();

            repartidorViewModel.setPedidoActual(pedido);

            // Crear el fragmento
            PedidoAceptadoFragment fragment = PedidoAceptadoFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putLong("pedidoId", pedido.getId());
            Log.d("tomarPedido", "Pedido ID: " + pedido.getId());

            clienteViewModel.findById(pedido.getClienteId()).observe(getViewLifecycleOwner(), cliente1 -> {
                    bundle.putString("clienteNombre", cliente1.getNombre());
                    Log.d("tomarPedido", "Cliente Nombre: " + cliente1.getNombre());

                    Log.e("tomarPedido", "Cliente no encontrado para el pedido.");


            });

            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frameContainer, fragment)
                    .addToBackStack(null)
                    .commit();


        }
    }

    private void setearDatos() {
        if (pedido != null && negocio != null && cliente != null) {
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            tvNroPedido.setText("Pedido #" + id);
            tvFechaPedido.setText(sf.format(pedido.getFechaPedido()));
            tvEstadoPedido.setText(pedido.getEstado());
            tvNombreNegocio.setText(negocio.getNombre());
            tvDireccionNegocio.setText(negocio.getDireccion().getDireccion());
            tvNombreCliente.setText(cliente.getNombre());
            tvDireccionCliente.setText(cliente.getDireccion().getDireccion());


            pedidoDetalleViewModel.getDetalleByPedido(pedido.getId()).observe(getViewLifecycleOwner(), detalle -> {
                adapter = new AdapterPedidoDetalle(getContext(), R.layout.item_list_pedidodetalle, (ArrayList<PedidoDetalle>) detalle, pedidoDetalleViewModel);
                listViewDetalle.setAdapter(adapter);
            });

        }

    }

    private void init(View rootView) {
        clienteViewModel = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider(requireActivity()).get(PedidoDetalleViewModel.class);
        pedidosViewModel=new ViewModelProvider(requireActivity()).get(PedidosViewModel.class);
        repartidorViewModel=new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        db = DatabaseApp.getInstance(getContext());
        tvNroPedido = rootView.findViewById(R.id.tvNroPedido);
        tvFechaPedido = rootView.findViewById(R.id.tvFechaPedido);
        tvNombreNegocio = rootView.findViewById(R.id.tvProducto);
        tvDireccionNegocio = rootView.findViewById(R.id.tvDireccionNegocio);
        tvNombreCliente = rootView.findViewById(R.id.tvPrecio);
        tvDireccionCliente = rootView.findViewById(R.id.tvDireccionCliente);
        btnConfirmarPedido = rootView.findViewById(R.id.btnConfirmarPedido);
        btnVolver = rootView.findViewById(R.id.btnVolver);
        listViewDetalle = rootView.findViewById(R.id.listViewDetalle);
        tvEstadoPedido = rootView.findViewById(R.id.tvEstadoPedido);


    }
}
