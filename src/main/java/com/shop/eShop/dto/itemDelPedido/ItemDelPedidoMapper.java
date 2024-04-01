package com.shop.eShop.dto.itemDelPedido;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.shop.eShop.entities.ItemDelPedido;

@Mapper(componentModel = "spring")
public interface ItemDelPedidoMapper {

    ItemDelPedidoMapper INSTANCE = Mappers.getMapper(ItemDelPedidoMapper.class);
    
    ItemDelPedido toEntity(ItemDelPedidoDTO itemDelPedidoDTO);
    ItemDelPedidoDTO toDTO(ItemDelPedido itemDelPedido);
}
