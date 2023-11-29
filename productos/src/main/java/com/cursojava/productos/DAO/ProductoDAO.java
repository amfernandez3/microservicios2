package com.cursojava.productos.DAO;

import com.cursojava.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoDAO extends JpaRepository<Producto, Integer> {

}
