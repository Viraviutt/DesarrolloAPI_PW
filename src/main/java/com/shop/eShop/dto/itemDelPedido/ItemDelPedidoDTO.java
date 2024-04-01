package com.shop.eShop.dto.itemDelPedido;

import com.shop.eShop.entities.Pedido;
import com.shop.eShop.entities.Producto;


public record ItemDelPedidoDTO(    
    Pedido idPedido,
    Producto idProducto, 
    Integer cantidad,
    Double precioUnitario
) {

} 
