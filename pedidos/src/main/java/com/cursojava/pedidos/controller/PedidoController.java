package com.cursojava.pedidos.controller;

import com.cursojava.pedidos.model.Pedido;
import com.cursojava.pedidos.service.PedidoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String PRODUCTO_SERVICE_URL = "http://localhost:8080/productos";

    // Endpoint para obtener la lista de todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerPedidos() {
        List<Pedido> pedidos = pedidoService.obtenerPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Endpoint para obtener un pedido por su ID
    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable int idPedido) {
        Pedido pedido = pedidoService.obtenerPedidoPorId(idPedido);

        if (pedido != null) {
            return new ResponseEntity<>(pedido, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/altaPedido")
    public ResponseEntity<Object> altaPedido(@RequestBody Pedido pedido) {
        try {
            boolean stockSuficiente = verificarStockProductos(pedido);
            if (stockSuficiente) {
                // Actualizar el stock de los productos
                actualizarStockProductos(pedido);
                // Crear el nuevo pedido
                Pedido nuevoPedido = pedidoService.crearPedido(pedido);
                return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
            } else {
                String mensajeError = "No hay suficiente stock del producto en el pedido.";
                System.out.println(mensajeError);
                return ResponseEntity
                        .badRequest()
                        .body(mensajeError);
            }
        } catch (Exception e) {
            String mensajeError = "Error interno al procesar el pedido. Detalles: " + e.getMessage();
            System.out.println(mensajeError);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(mensajeError);
        }
    }

    private boolean verificarStockProductos(Pedido pedido) {
        int codigoProducto = pedido.getCodigoProducto();
        int unidadesPedido = pedido.getUnidades();

        // Construir la URL
        String url = PRODUCTO_SERVICE_URL + "/actualizarStock/{codigoProducto}?unidadesCompradas={unidadesPedido}";

        // Realizar llamada al servicio de Producto para obtener información del producto
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                String.class,
                codigoProducto, unidadesPedido);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            try {
                // Convertir la respuesta JSON a un objeto JsonNode
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

                // Obtener el valor del campo "stock" como texto
                String stockTexto = jsonNode.get("stock").asText();

                // Convertir el valor del campo "stock" a entero
                int stockActual = Integer.parseInt(stockTexto);
                System.out.println("Stock actual del producto: " + stockActual);
                return unidadesPedido <= stockActual;
            } catch (Exception e) {
                // Manejar cualquier error durante la conversión
                String mensajeError = "Error al convertir el stock a entero. Respuesta del servicio: " + responseEntity.getBody();
                System.out.println(mensajeError);
                return false;
            }
        } else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
            // Manejar el caso específico de código de estado 400 (Bad Request)
            String mensajeError = "No hay suficiente stock del producto en el pedido. Respuesta del servicio: " + responseEntity.getBody();
            System.out.println(mensajeError);
            return false;
        } else {
            String mensajeError = "Error al obtener el stock del producto. Respuesta del servicio: " + responseEntity.getBody();
            System.out.println(mensajeError);
            return false;
        }
    }

    // Función para actualizar el stock de los productos después de realizar un pedido
    private void actualizarStockProductos(Pedido pedido) {
        int codigoProducto = pedido.getCodigoProducto();
        int unidadesPedido = pedido.getUnidades();

        // Realizar llamada al servicio de Producto para actualizar el stock
        restTemplate.exchange(
                PRODUCTO_SERVICE_URL + "/actualizarStock/{codigoProducto}?unidadesCompradas={unidadesPedido}",
                HttpMethod.PUT,
                null,
                Void.class,
                codigoProducto, unidadesPedido);

        System.out.println("Stock de productos actualizado con éxito.");
    }



    // Endpoint para eliminar un pedido
    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable int idPedido) {
        pedidoService.eliminarPedido(idPedido);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para actualizar un pedido
    @PutMapping("/{idPedido}")
    public ResponseEntity<Pedido> actualizarPedido(@PathVariable int idPedido, @RequestBody Pedido pedido) {
        Pedido pedidoActualizado = pedidoService.actualizarPedido(idPedido, pedido);

        if (pedidoActualizado != null) {
            return new ResponseEntity<>(pedidoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
