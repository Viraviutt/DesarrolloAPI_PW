package com.shop.eShop.dto.producto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.shop.eShop.entities.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);
    
    Producto toEntity(ProductoDTO productoDTO);
    ProductoDTO toDTO(Producto producto);
}
