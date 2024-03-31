package com.shop.eShop.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Cliente;
import com.shop.eShop.entities.Pedido;
import com.shop.eShop.enums.Estado;

@RepositoryRestController
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /* find by pedidos de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido = ?1")
    List<Pedido> findByFecha(Timestamp fechaPedido);

    /* find by pedidos antes de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido < ?1")
    List<Pedido> findByFechaBefore(Timestamp fechaPedido);

    /* find by pedidos despues de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido > ?1")
    List<Pedido> findByFechaAfter(Timestamp fechaPedido);

    /* find by pedidos entre fechas */
    @Query("SELECT p FROM Pedido p WHERE p.fechaPedido BETWEEN ?1 AND ?2")
    List<Pedido> findByFechaBetween(Timestamp fechaPedido1, Timestamp fechaPedido2);

    /* find by pedidos de un cliente */
    @Query("SELECT p FROM Pedido p WHERE p.idCliente = ?1")
    Cliente findByCliente(Cliente idCliente);

    /* find by cliente y estado */
    @Query("SELECT p FROM Pedido p WHERE p.idCliente = ?1 AND p.estado = ?2")
    List<Pedido> findByClienteAndEstado(Cliente idCliente, Estado estado);

    /* find by estado */
    @Query("SELECT p FROM Pedido p WHERE p.estado = ?1")
    List<Pedido> findByEstado(Estado estado);
    

}
