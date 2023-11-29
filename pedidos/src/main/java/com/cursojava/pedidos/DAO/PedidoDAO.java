package com.cursojava.pedidos.DAO;

import com.cursojava.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDAO extends JpaRepository<Pedido, Integer> {
}
