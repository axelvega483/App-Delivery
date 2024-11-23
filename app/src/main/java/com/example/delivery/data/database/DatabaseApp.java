package com.example.delivery.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.delivery.data.dao.CategoriaDAO;
import com.example.delivery.data.dao.ClienteDAO;
import com.example.delivery.data.dao.NegocioDAO;
import com.example.delivery.data.dao.PedidoDAO;
import com.example.delivery.data.dao.PedidoDetalleDAO;
import com.example.delivery.data.dao.ProductoDAO;
import com.example.delivery.data.dao.RepartidorDAO;
import com.example.delivery.data.dao.SeguimientoDAO;
import com.example.delivery.data.model.Categoria;
import com.example.delivery.data.model.Cliente;
import com.example.delivery.data.model.Negocio;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.data.model.Producto;
import com.example.delivery.data.model.Repartidor;
import com.example.delivery.data.model.Seguimiento;

@Database(entities = {
        Repartidor.class,
        Cliente.class,
        Producto.class,
        Negocio.class,
        Categoria.class,
        Pedido.class,
        PedidoDetalle.class,
        Seguimiento.class
}, version = 1)


public abstract class DatabaseApp extends RoomDatabase {
    private static final String BD_NAME = "db_delivery_chopify";
    private static volatile DatabaseApp instance;

    public abstract RepartidorDAO repartidorDAO();

    public abstract ClienteDAO clienteDAO();

    public abstract CategoriaDAO categoriaDAO();

    public abstract NegocioDAO negocioDAO();

    public abstract PedidoDAO pedidoDAO();

    public abstract PedidoDetalleDAO pedidoDetalleDAO();

    public abstract ProductoDAO productoDAO();

    public abstract SeguimientoDAO seguimientoDAO();


    public static synchronized DatabaseApp getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseApp.class, BD_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
            insertarDatosEjemplo(context);
        }
        return instance;
    }


    public static void insertarDatosEjemplo(Context context) {
        DatabaseApp db = DatabaseApp.getInstance(context);

        //ESTE METODO ES PARA CARGAR SIMULACION DE PEDIDOS ETC
    }

}
