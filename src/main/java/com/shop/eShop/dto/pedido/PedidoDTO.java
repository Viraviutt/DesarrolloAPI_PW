package com.shop.eShop.dto.pedido;

import java.sql.Timestamp;

import com.shop.eShop.entities.Cliente;


public record PedidoDTO(
    Long idPedido, 
    Cliente idCliente, 
    Timestamp fechaPedido, 
    String estado
) {

}
