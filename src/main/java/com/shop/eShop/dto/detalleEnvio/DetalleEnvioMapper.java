package com.shop.eShop.dto.detalleEnvio;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.shop.eShop.entities.DetalleEnvio;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {

    DetalleEnvioMapper INSTANCE = Mappers.getMapper(DetalleEnvioMapper.class);

    DetalleEnvio toEntity(DetalleEnvioDTO detalleEnvioDTO);
    DetalleEnvioDTO toDTO(DetalleEnvio detalleEnvio);
}
