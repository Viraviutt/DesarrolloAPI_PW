package com.shop.eShop.dto.pago;

import java.sql.Timestamp;

import com.shop.eShop.entities.Pedido;

public record PagoDTO(
    Long idPago,
    Pedido idPedido,
    Double monto,
    Timestamp fechaPago,
    String metodoPago
) {
    
}
