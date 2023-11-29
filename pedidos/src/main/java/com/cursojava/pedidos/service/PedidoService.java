package com.cursojava.pedidos.service;
import com.cursojava.pedidos.DAO.PedidoDAO;
import com.cursojava.pedidos.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    // Funciones CRUD

    public Pedido crearPedido(Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.findAll();
    }

    public void eliminarPedido(int idPedido) {
        pedidoDAO.deleteById(idPedido);
    }

    public Pedido actualizarPedido(int idPedido, Pedido pedido) {
        Optional<Pedido> pedidoExistente = pedidoDAO.findById(idPedido);
        if (pedidoExistente.isPresent()) {
            Pedido pedidoEncontrado = pedidoExistente.get();
            pedidoEncontrado.setCodigoProducto(pedido.getCodigoProducto());
            pedidoEncontrado.setUnidades(pedido.getUnidades());
            pedidoEncontrado.setTotal(pedido.getTotal());
            pedidoEncontrado.setFechaPedido(pedido.getFechaPedido());
            return pedidoDAO.save(pedidoEncontrado);
        } else {
            return null;
        }
    }

    // Funciones extra

    public List<Pedido> crearPedidos(List<Pedido> pedidos) {
        return pedidoDAO.saveAll(pedidos);
    }

    public Pedido obtenerPedidoPorId(int idPedido) {
        return pedidoDAO.findById(idPedido).orElse(null);
    }
}
