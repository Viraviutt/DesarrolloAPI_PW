package com.shop.eShop.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import com.shop.eShop.entities.ItemDelPedido;
import com.shop.eShop.entities.Producto;

@RepositoryRestController
public interface ItemDelPedidoRepository extends JpaRepository<ItemDelPedido, Long> {

    /* find by id del pedido */
    @Query("SELECT i FROM ItemDelPedido i WHERE i.idPedido.idPedido = ?1")
    List<ItemDelPedido> findByIdDelPedido(Long id);

    /* find by producto */
    @Query("SELECT i FROM ItemDelPedido i WHERE i.idProducto = ?1")
    List<ItemDelPedido> findByIdDelProducto(Producto Producto);

    /* calc sum for total de ventas para un producto */
    @Query("SELECT SUM(i.precioUnitario)*i.cantidad FROM ItemDelPedido i WHERE i.idProducto = ?1")
    Double calcSumForTotalDeVentas(Producto Producto);

}
