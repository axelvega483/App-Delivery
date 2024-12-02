package com.example.delivery.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
import com.example.delivery.data.model.PedidoDetalle;
import com.example.delivery.ui.viewmodel.ClienteViewModel;
import com.example.delivery.ui.viewmodel.PedidoDetalleViewModel;
import com.example.delivery.ui.viewmodel.PedidosViewModel;
import com.example.delivery.ui.viewmodel.RepartidorViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;



public class PedidoAceptadoFragment extends Fragment implements OnMapReadyCallback {
    private EditText edCodigoEntrega;
    private TextView tvNroPedido, tvNombreCliente, tvTotal;
    private Button btnConfirmarEntrega, btnVolver;

    private RepartidorViewModel repartidorViewModel;
    private PedidoDetalleViewModel pedidoDetalleViewModel;
    private ClienteViewModel clienteViewModel;
    private PedidosViewModel pedidosViewModel;
    private Pedido pedidoActual;
// maps
    private GoogleMap mapViewPedido;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean isFirstUpdate = true;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LatLng clienteCoords;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pedido_aceptado, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapViewPedido);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        // Solo inicializamos si no se ha hecho ya
        init(root);
        initListener();
        setearDatos();
        return root;
    }

    private void setearDatos() {
        // Actualizar datos del pedido
        repartidorViewModel.getPedidoActual().observe(getViewLifecycleOwner(), pedido -> {
            if (pedido != null) {

                this.pedidoActual = pedido;
                tvNroPedido.setText("Pedido #" + pedido.getId() + "\nCodigo: " + pedido.getCodigoEntrega());

                // Obtener detalles del pedido
                pedidoDetalleViewModel.getDetalleByPedido(pedido.getId()).observe(getViewLifecycleOwner(), detalles -> {
                    double total = 0.0;
                    for (PedidoDetalle detalle : detalles) {
                        Log.e("TOTAL", "subtotal: " + detalle.getSubtotal());
                        Log.e("TOTAL", "cantidad: " + detalle.getCantidad());
                        total += detalle.getSubtotal();
                    }
                    tvTotal.setText(String.format("$ %.2f", total));
                });

                // Obtener datos del cliente
                clienteViewModel.findById(pedido.getClienteId()).observe(getViewLifecycleOwner(), cliente -> {
                    if (cliente != null && cliente.getDireccion()!=null) {
                        tvNombreCliente.setText(cliente.getNombre() + " " + cliente.getApellido());
                        clienteCoords = new LatLng(cliente.getDireccion().getLatitud(), cliente.getDireccion().getLongitud());
                        Log.e("LATITUD", "latitud:"+cliente.getDireccion().getLatitud());
                        Log.e("LONGITUD", "longitud:"+cliente.getDireccion().getLongitud());
                    } else {
                        tvNombreCliente.setText("Cliente no encontrado");
                    }

                });

            }

        });
    }


    private void initListener() {
        btnConfirmarEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pedidoActual.getCodigoEntrega().equals(edCodigoEntrega.getText().toString())) {
                    Toast.makeText(requireActivity(), "Entrega Confirmada", Toast.LENGTH_SHORT).show();
                    pedidoActual.setEstado("Entregado");
                    pedidosViewModel.update(pedidoActual);
                    repartidorViewModel.setPedidoActual(null);
                    pedidoActual = null;

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainer, PedidosFragment.newInstance())
                            .commit();


                } else {
                    Toast.makeText(requireActivity(), "El codigo no es igual", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, PedidosFragment.newInstance()).commit();
            }
        });
    }

    private void init(View root) {
        // Inicializar ViewModels
        repartidorViewModel = new ViewModelProvider(requireActivity()).get(RepartidorViewModel.class);
        pedidoDetalleViewModel = new ViewModelProvider(requireActivity()).get(PedidoDetalleViewModel.class);
        clienteViewModel = new ViewModelProvider(requireActivity()).get(ClienteViewModel.class);
        pedidosViewModel = new ViewModelProvider(requireActivity()).get(PedidosViewModel.class);
        edCodigoEntrega = root.findViewById(R.id.edCodigoEntrega);
        btnConfirmarEntrega = root.findViewById(R.id.btnConfirmarEntrega);
        btnVolver = root.findViewById(R.id.btnVolver);
        tvNombreCliente = root.findViewById(R.id.tvNombreCliente);
        tvNroPedido = root.findViewById(R.id.tvNroPedido);
        tvTotal = root.findViewById(R.id.tvTotal);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapViewPedido = googleMap;
        repartidorViewModel.getPedidoActual().observe(getViewLifecycleOwner(), pedido -> {
            clienteViewModel.findById(pedido.getClienteId()).observe(getViewLifecycleOwner(), cliente -> {
                if (cliente != null && cliente.getDireccion() != null) {
                    // Asigna las coordenadas del cliente
                    clienteCoords = new LatLng(cliente.getDireccion().getLatitud(), cliente.getDireccion().getLongitud());
                    Log.e("LATITUD", "latitud:" + cliente.getDireccion().getLatitud());
                    Log.e("LONGITUD", "longitud:" + cliente.getDireccion().getLongitud());

                    // Mueve el log y la adición del marcador aquí para asegurarte de que clienteCoords ya tiene valor
                    Log.e("Cliente Coordenadas", "Cliente Coordenadas: " + clienteCoords);

                    // Agregar marcador del cliente ahora que clienteCoords ya no es null
                    if (clienteCoords != null) {
                        mapViewPedido.addMarker(new MarkerOptions().position(clienteCoords).title("Cliente"));
                    }
                }
            });
        });
        Log.e("Cliente Coordenadas","Cliente Coordenadas"+clienteCoords);
        // Agregar marcador del cliente (asegúrate de inicializar clienteCoords con la latitud y longitud)
        if (clienteCoords != null) {
            mapViewPedido.addMarker(new MarkerOptions().position(clienteCoords).title("Cliente"));
        }

        // Verificar permisos de ubicación y comenzar a rastrear
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(requireContext(), "Permiso de ubicación requerido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest.Builder(
                LocationRequest.PRIORITY_HIGH_ACCURACY, 5000)  // Intervalo de 5 segundos
                .setMinUpdateIntervalMillis(2000)  // Actualización más rápida en 2 segundos
                .build();

        locationCallback = new LocationCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        LatLng repartidorCoords = new LatLng(location.getLatitude(), location.getLongitude());
                        // Actualizar posición del marcador del repartidor
                        if (isFirstUpdate) {
                            mapViewPedido.setMyLocationEnabled(true);
                            mapViewPedido.animateCamera(CameraUpdateFactory.newLatLngZoom(repartidorCoords, 15));
                            isFirstUpdate = false;  // Cambiar la bandera a false después de la primera actualización

                            // Después de centrar, detenemos las actualizaciones automáticas
                            stopLocationUpdates();
                        }
                    }
                }
            }
        };

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }


    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopLocationUpdates();  // Detener las actualizaciones cuando el fragmento se destruye
    }
}