package com.example.delivery.data.database;


import android.content.Context;
import android.util.Log;

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
import com.example.delivery.data.model.Direccion;
import com.example.delivery.data.model.Negocio;
import com.example.delivery.data.model.Pedido;
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.data.model.Producto;
import com.example.delivery.data.model.Repartidor;
import com.example.delivery.data.model.Seguimiento;

import java.util.Date;
import java.util.concurrent.Executors;

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
        Executors.newSingleThreadExecutor().execute(() -> {
            Cliente cliente = new Cliente();
            cliente.setNombre("Juan");
            cliente.setApellido("Perez");
            cliente.setEmail("juan@gmail.com");
            cliente.setTelefono("123456789");

            Direccion direccionCliente = new Direccion();
            direccionCliente.setDireccion("Av. Roca Sur 930");
            direccionCliente.setLatitud(-27.7878508f);
            direccionCliente.setLongitud(-64.2537045f);

            Log.e("Direccion", direccionCliente.toString());

            cliente.setDireccion(direccionCliente);
            Long clienteId = db.clienteDAO().save(cliente);
            cliente.setId(clienteId);

            Log.e("Cliente", cliente.toString());


            Negocio negocio = new Negocio();
            negocio.setNombre("Antares");
            Direccion direccionNegocio = new Direccion();
            direccionNegocio.setDireccion("Pellegrini 480");
            negocio.setDireccion(direccionNegocio);
            Long negocioId = db.negocioDAO().save(negocio);
            negocio.setId(negocioId);
            Log.e("Negocio", negocio.toString());

            Categoria categoria = new Categoria();
            categoria.setNombre("Cerveza");
            Long categoriaId = db.categoriaDAO().save(categoria);
            categoria.setId(categoriaId);
            Log.e("Categoria", categoria.toString());

            Producto producto = new Producto();
            producto.setNombre("Cerveza Rubia");
            producto.setPrecio(1500);
            producto.setCategoriaId(categoria.getId());
            Long productoId = db.productoDAO().save(producto);
            producto.setId(productoId);
            Log.e("Producto", producto.toString());

            Pedido pedido = new Pedido();
            pedido.setClienteId(cliente.getId());
            pedido.setNegocioId(negocio.getId());
            pedido.setFechaPedido(new Date());
            pedido.setEstado("Pendiente");
            Long pedidoId = db.pedidoDAO().save(pedido);
            pedido.setId(pedidoId);
            Log.e("Pedido", pedido.toString());

            PedidoDetalle pedidoDetalle = new PedidoDetalle();
            pedidoDetalle.setPedidoId(pedido.getId());
            pedidoDetalle.setProductoId(producto.getId());
            pedidoDetalle.setCantidad(2);
            Long pedidoDetalleId = db.pedidoDetalleDAO().save(pedidoDetalle);
            pedidoDetalle.setId(pedidoDetalleId);
            Log.e("PedidoDetalle", pedidoDetalle.toString());

        });
    }

}
