package com.shop.eShop.dto.cliente;


public record ClienteDTO(
    Long idCliente,
    String nombre,
    String correo
) {
    
}
