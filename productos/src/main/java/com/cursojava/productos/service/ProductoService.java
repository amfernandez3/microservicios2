package com.cursojava.productos.service;

import com.cursojava.productos.DAO.ProductoDAO;
import com.cursojava.productos.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    @Autowired
    ProductoDAO productoDAO;

    //***************************************************** Funciones CRUD
    /**
     * Permite crear un producto en db
     * @param producto Se le pasa un producto y este lo añade a la tabla
     * @return devuelve la función de guardado
     */
    public Producto crearProducto(Producto producto){
        return productoDAO.save(producto);
    }

    /**
     * Permite obtener la lista de todos los productos
     * @return devuelve la lista
     */
    public List<Producto> obtenerProductos(){
        return productoDAO.findAll();
    }

    /**
     * Permite eliminar un curso usando el ia
     * @param productoId El id del producto a eliminar
     */
    public void eliminarProducto(int productoId) {
        productoDAO.deleteById(productoId);
    }

    /**
     * Función que permite modificar los datos de un curso
     * @param productoId el identificador del producto
     * @param producto el producto cuyos datos se guardarán
     * @return
     */
    public Producto actualizarProducto(int productoId, Producto producto) {
        Optional<Producto> productoExistente = productoDAO.findById(productoId);
        if (productoExistente.isPresent()) {
            Producto productoEncontrado = productoExistente.get();
            productoEncontrado.setProducto(producto.getProducto());
            productoEncontrado.setPreciounitario(producto.getPreciounitario());
            productoEncontrado.setStock(producto.getStock());
            return productoDAO.save(productoEncontrado);
        } else {
            return null;
        }
    }


    //****************************************************** Funciones extra

    /**
     * Permite crear productos en db
     * @param productos Se le pasa una lista de productos
     * @return devuelve la función de guardado
     */
    public List<Producto> crearProductos(List<Producto> productos){
        return productoDAO.saveAll(productos);
    }

    /**
     * Funcion que permite modificar el stock de un producto seleccionado por id
     * @param idProducto id del producto a modificar
     * @param nuevoStock nuevo valor del atributo stock
     * @return
     */
    public Producto modificarStockProducto(int idProducto, int nuevoStock){
        Optional<Producto> productoAmodificarStock = productoDAO.findById(idProducto);
        if(productoAmodificarStock.isPresent()){
            Producto productoModificado = productoAmodificarStock.get();
            productoModificado.setStock(nuevoStock);
            return productoDAO.save(productoModificado);
        }
        else {
            return null;
        }

    }

    /**
     * Funcion que permite obtener un producto por id
     * @param codigoProducto identificador del producto
     * @return devuelve el producto
     */
    public Producto obtenerProductoPorCodigo(int codigoProducto) {
        return productoDAO.findById(codigoProducto).orElse(null);
    }

    /**
     * Metodo que permite buscar un producto por Id
     * @param codigoProducto id por el que se buscará el producto
     * @return devuelve el producto
     */
    public Producto obtenerProductoPorId(int codigoProducto) {
        return productoDAO.findById(codigoProducto).orElse(null);
    }
}
