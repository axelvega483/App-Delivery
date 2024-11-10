package com.example.delivery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.delivery.R;
import com.example.delivery.model.Pedido;

import java.util.ArrayList;

public class AdapterPedidos extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Pedido> pedidos;

    public AdapterPedidos(Context context, int layout, ArrayList<Pedido> pedidos) {
        this.context = context;
        this.layout = layout;
        this.pedidos = pedidos;
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
        nroPedido.setText(pedido.getId().toString());

        TextView negocio=v.findViewById(R.id.tvNombreNegocio);
        negocio.setText(pedido.getNegocio().getNombre());

        TextView cliente=v.findViewById(R.id.tvNombreCliente);
        cliente.setText(pedido.getCliente().getNombre());

        TextView direcCliente=v.findViewById(R.id.tvDireccionCliente);
        direcCliente.setText("Diureccion cleinte");

        TextView estado=v.findViewById(R.id.tvEstado);
        estado.setText("PENDIENTE");
        return v;
    }
}
