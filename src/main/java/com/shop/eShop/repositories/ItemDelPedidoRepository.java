package com.shop.eShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.ItemDelPedido;

@RepositoryRestController
public interface ItemDelPedidoRepository extends JpaRepository<ItemDelPedido, Long> {

    /* find by id del pedido */
    @Query("SELECT i FROM ItemDelPedido i WHERE i.idDelPedido = ?1")
    List<ItemDelPedido> findByIdDelPedido(Long id);

    /* find by id del producto */
    @Query("SELECT i FROM ItemDelPedido i WHERE i.idDelProducto = ?1")
    List<ItemDelPedido> findByIdDelProducto(Long id);


}
