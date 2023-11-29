package com.cursojava.productos.controller;
import com.cursojava.productos.model.Producto;
import com.cursojava.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/{codigoProducto}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int codigoProducto) {
        Producto producto = productoService.obtenerProductoPorId(codigoProducto);

        if (producto != null) {
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para obtener la lista de todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerProductos() {
        List<Producto> productos = productoService.obtenerProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // Endpoint para crear un nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.crearProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    // Dentro de la clase ProductoController

    @PostMapping("/crearProductos")
    public ResponseEntity<List<Producto>> crearProductos(@RequestBody List<Producto> productos) {
        List<Producto> nuevosProductos = productoService.crearProductos(productos);
        return new ResponseEntity<>(nuevosProductos, HttpStatus.CREATED);
    }



    // Endpoint para actualizar el stock de un producto
    //localhost:8080/productos/actualizarStock/{productoId}?unidadesCompradas={unidadesCompradas} (PUT)
    @PutMapping("/actualizarStock/{productoId}")
    public ResponseEntity<Producto> actualizarStockProducto(
            @PathVariable int productoId,
            @RequestParam("unidadesCompradas") int unidadesCompradas) {

        Producto productoActualizado = productoService.modificarStockProducto(productoId, unidadesCompradas);

        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Otros endpoints para eliminar y actualizar productos según tus necesidades

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable int productoId) {
        productoService.eliminarProducto(productoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int productoId,
            @RequestBody Producto producto) {

        Producto productoActualizado = productoService.actualizarProducto(productoId, producto);

        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Funcion que permite buscar un producto por id y mostrar su precio unitario
    @GetMapping("/{codigoProducto}/precioUnitario")
    public ResponseEntity<Double> obtenerPrecioUnitario(@PathVariable int codigoProducto) {
        Producto producto = productoService.obtenerProductoPorCodigo(codigoProducto);

        if (producto != null) {
            double precioUnitario = producto.getPreciounitario();
            return new ResponseEntity<>(precioUnitario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
