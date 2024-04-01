package com.shop.eShop.dto.pago;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.shop.eShop.entities.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    PagoMapper INSTANCE = Mappers.getMapper(PagoMapper.class);
    
    @Mapping(target = "idPago", ignore = true)
    Pago toEntity(PagoDTO pagoDTO);
    PagoDTO toDTO(Pago pago);
}
