package com.shop.eShop.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.Pedido;
import com.shop.eShop.enums.Estado;

@RepositoryRestController
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /* find by pedidos de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fecha = ?1")
    List<Pedido> findByFecha(Timestamp fecha);

    /* find by pedidos antes de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fecha < ?1")
    List<Pedido> findByFechaBefore(Timestamp fecha);

    /* find by pedidos despues de una fecha */
    @Query("SELECT p FROM Pedido p WHERE p.fecha > ?1")
    List<Pedido> findByFechaAfter(Timestamp fecha);

    /* find by estado */
    @Query("SELECT p FROM Pedido p WHERE p.estado = ?1")
    List<Pedido> findByEstado(Estado estado);

}
