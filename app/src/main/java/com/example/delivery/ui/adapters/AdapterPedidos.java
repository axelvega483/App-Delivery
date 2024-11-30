package com.example.delivery.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;
import com.example.delivery.ui.fragments.VerPedidoFragment;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.ui.viewmodel.PedidosViewModel;

import java.util.ArrayList;

public class AdapterPedidos extends BaseAdapter {
    private Context context;
    private int layout;
    private PedidosViewModel pedidosViewModel;
    private ArrayList<Pedido> pedidos;
    public AdapterPedidos(Context context, int layout, ArrayList<Pedido> pedidos, PedidosViewModel pedidosViewModel) {
        this.context = context;
        this.layout = layout;
        this.pedidos = pedidos;
        this.pedidosViewModel = pedidosViewModel;
    }

    @Override
    public int getCount() {
        return this.pedidos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pedidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v=view;
        LayoutInflater layoutInflater=LayoutInflater.from(this.context);
        v=layoutInflater.inflate(R.layout.item_list_pedido,null);

        Pedido pedido=pedidos.get(position);
        TextView nroPedido=v.findViewById(R.id.nroPedido);
        nroPedido.setText("Pedido #"+pedido.getId().toString());

        TextView negocio=v.findViewById(R.id.tvProducto);
        pedidosViewModel.getNegocioById(pedido.getNegocioId()).observe((PrincipalActivity) context, negocioData -> {
            if (negocioData != null && negocioData.getNombre() != null) {
                negocio.setText(negocioData.getNombre());
            }
        });
        TextView direccionNegocio=v.findViewById(R.id.tvCantidad);
        pedidosViewModel.getNegocioById(pedido.getNegocioId()).observe((PrincipalActivity) context, negocioData -> {
            if (negocioData != null && negocioData.getNombre() != null) {
                direccionNegocio.setText(negocioData.getDireccion().getDireccion());
            }
        });

        TextView cliente=v.findViewById(R.id.tvPrecio);
        pedidosViewModel.getClienteById(pedido.getClienteId()).observe((PrincipalActivity) context, clienteData -> {
            if (clienteData != null && clienteData.getNombre() != null) {
                cliente.setText(clienteData.getNombre());
            }
        });

        TextView direcCliente=v.findViewById(R.id.tvDireccionCliente);
        pedidosViewModel.getClienteById(pedido.getClienteId()).observe((PrincipalActivity) context,direcClienteData -> {
            if (direcClienteData != null && direcClienteData.getDireccion() != null) {
                direcCliente.setText(direcClienteData.getDireccion().getDireccion());
            }
        });

        TextView estado=v.findViewById(R.id.tvEstado);
        estado.setText(pedido.getEstado());

        // En AdapterPedidos.java
        CardView cardView = v.findViewById(R.id.cardViewPedido);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el fragmento de detalle con los datos del pedido
                VerPedidoFragment detailFragment = VerPedidoFragment.newInstance(
                        pedido.getId().toString(),
                        negocio.getText().toString(),
                        direccionNegocio.getText().toString(),
                        cliente.getText().toString(),
                        direcCliente.getText().toString(),
                        pedido.getEstado().toString()
                );

                // Verificamos si el contexto es una instancia de PrincipalActivity
                if (context instanceof PrincipalActivity) {
                    // Llamamos al método openFragment desde PrincipalActivity
                    ((PrincipalActivity) context).openFragment(detailFragment);
                }
            }});
        return v;
    }

}
