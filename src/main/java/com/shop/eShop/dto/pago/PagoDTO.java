package com.shop.eShop.dto.pago;

import java.sql.Timestamp;

import com.shop.eShop.entities.Pedido;
import com.shop.eShop.enums.metodoPago;

public record PagoDTO(
    Pedido idPedido,
    Double monto,
    Timestamp fechaPago,
    metodoPago metodoPago
) {
    
}
