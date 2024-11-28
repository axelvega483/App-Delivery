package com.example.delivery.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.delivery.PrincipalActivity;
import com.example.delivery.R;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.ui.viewmodel.PedidoDetalleViewModel;
import com.example.delivery.ui.viewmodel.PedidosViewModel;

import java.util.ArrayList;

public class AdapterPedidoDetalle  extends BaseAdapter {
    private Context context;
    private int layout;
    private PedidoDetalleViewModel pedidoDetalleViewModel;
    private ArrayList<PedidoDetalle> pedidoDetalles;

    public AdapterPedidoDetalle(Context context, int layout, ArrayList<PedidoDetalle> pedidoDetalles, PedidoDetalleViewModel pedidoDetalleViewModel) {
        this.context = context;
        this.layout = layout;
        this.pedidoDetalles = pedidoDetalles;
        this.pedidoDetalleViewModel = pedidoDetalleViewModel;
    }
    @Override
    public int getCount() {
        return this.pedidoDetalles.size();
    }

    @Override
    public Object getItem(int i) {
        return this.pedidoDetalles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View v = view;
        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            v = layoutInflater.inflate(R.layout.item_list_pedidodetalle, viewGroup, false);
        }

        PedidoDetalle pedidoDetalle = pedidoDetalles.get(position);

        // Obteniendo las vistas directamente para no repetir la búsqueda en cada ciclo
        TextView producto = v.findViewById(R.id.tvProducto);
        TextView cantidad = v.findViewById(R.id.tvCantidad);
        TextView precio = v.findViewById(R.id.tvPrecio);

        // Asegúrate de que la observación del LiveData se hace correctamente
        pedidoDetalleViewModel.getProductoById(pedidoDetalle.getProductoId()).observe((PrincipalActivity) context, productoData -> {
            if (productoData != null && productoData.getNombre() != null) {
                // Actualizar los valores solo si los datos no son nulos
                producto.setText(productoData.getNombre());
                cantidad.setText(String.valueOf(pedidoDetalle.getCantidad()));  // Debes convertir cantidad a String
                precio.setText(String.valueOf(productoData.getPrecio()));
            } else {
                // Si los datos no están disponibles, mostrar un valor predeterminado
                producto.setText("Producto no disponible");
                cantidad.setText("0");
                precio.setText("0");
            }

        });



        return v;
    }
}
