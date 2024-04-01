package com.shop.eShop.dto.pedido;

import java.sql.Timestamp;

import com.shop.eShop.entities.Cliente;
import com.shop.eShop.enums.Estado;


public record PedidoDTO(
    Long idPedido, 
    Cliente idCliente, 
    Timestamp fechaPedido, 
    Estado estado
) {

}
