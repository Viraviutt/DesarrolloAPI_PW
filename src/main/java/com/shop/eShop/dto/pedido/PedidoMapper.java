package com.shop.eShop.dto.pedido;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.shop.eShop.entities.Pedido;

@Mapper
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);
    
    Pedido toEntity(PedidoDTO pedidoDTO);
    PedidoDTO toDTO(Pedido pedido);
}
