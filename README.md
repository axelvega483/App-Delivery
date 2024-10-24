Delivery de Comida -Requerimientos Funcionales para Repartidor
# 1)Gestión de Pedidos
  ## Asignación de Pedidos:

   - Recepción de nuevos pedidos asignados automáticamente en función de la ubicación del repartidor y la disponibilidad.

   - Detalles del pedido: cliente, Local Comercial, dirección de entrega, productos.

  ## Actualización de Estado:

   - El repartidor debe poder actualizar el estado del pedido en tiempo real:

       Pedido aceptado, Pedido recogido, En camino, Pedido entregado

  ## Notificaciones Push:
   
   - Notificaciones push para informar al repartidor cuando se le asigne un nuevo pedido o cuando haya actualizaciones importantes.

  ##  Historial de Pedidos:
  
   - Listado de pedidos anteriores con detalles como fecha, hora, dirección y estado final (entregado, cancelado).

# 2) Geolocalización y Rutas

   - Mapa en Tiempo Real:

       El repartidor podrá ver en un mapa la ubicación del cliente y del restaurante para optimizar la ruta.

       Integración con Google Maps para la visualización y seguimiento del recorrido.

   - Seguimiento del Repartidor:

        El cliente podrá seguir la ubicación del repartidor en tiempo real a través de la aplicación.

# 3) Interacción con el Cliente

   - Comunicación:

        Funcionalidad para que el repartidor pueda contactar al cliente por teléfono o mediante mensajes en la app en caso de necesitar coordinar detalles de la entrega.
   
  - Confirmación de Entrega:

       El repartidor debe confirmar la entrega del pedido mediante la firma o código proporcionado por el cliente.

# 4) Notificaciones y Alertas

   - Actualizaciones del Pedido:

       Notificaciones push al cliente cuando el pedido cambia de estado (en camino, entregado, etc.).

# 5) Autenticación

   - Registro y Autenticación del Repartidor:

        Registro de nuevos repartidores con validación de correo electrónico y teléfono.

  - Inicio de Sesión:

       Permitir el inicio de sesión y recordar sesión para evitar la autenticación constante.
