package com.shop.eShop.dto.producto;


public record ProductoDTO(
    Long idProducto, 
    String nombre, 
    Double precio, 
    Integer stock
) {
    
}
