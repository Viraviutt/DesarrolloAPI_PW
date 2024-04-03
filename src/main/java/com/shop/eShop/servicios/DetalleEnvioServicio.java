package com.shop.eShop.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.eShop.dto.detalleEnvio.DetalleEnvioDTO;
import com.shop.eShop.dto.detalleEnvio.DetalleEnvioMapper;
import com.shop.eShop.entities.DetalleEnvio;
import com.shop.eShop.repositories.DetalleEnvioRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class DetalleEnvioServicio {

    @Autowired
    private DetalleEnvioRepository detalleEnvioRepository;

    public List<DetalleEnvioDTO> getAllDetalleEnvios() {

        try {

            List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findAll();
            return detalleEnvios.stream().map(DetalleEnvioMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los detalleEnvios", e);

        }

        return List.of();

    }

    public DetalleEnvioDTO getDetalleEnvioById(Long id) {

        try {

            DetalleEnvio detalleEnvio = detalleEnvioRepository.findById(id).orElse(null);
            return DetalleEnvioMapper.INSTANCE.toDTO(detalleEnvio);

        } catch (Exception e) {

            log.error("Error obteniendo detalleEnvio por id",e);

        }

        return null;
    }

    public DetalleEnvioDTO getDetalleEnvioByIdPedido(Long id) {

        try {

            DetalleEnvio detalleEnvios = detalleEnvioRepository.findByIdDelPedido(id).orElse(null);
            return DetalleEnvioMapper.INSTANCE.toDTO(detalleEnvios);

        } catch (Exception e){

            log.error("Error obteniendo detalleEnvio por transportadora", e);

        }

        return null;
    }

    public List<DetalleEnvioDTO> getDetalleEnvioByTransportadora(String transportadora) {

        try {

            List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findByTransportadora(transportadora).orElse(null);
            return detalleEnvios.stream().map(DetalleEnvioMapper.INSTANCE::toDTO).toList();

        } catch (Exception e){

            log.error("Error obteniendo detalleEnvio por transportadora", e);

        }

        return List.of();
    }

    public DetalleEnvioDTO getDetalleEnvioByNumRastreo(String numRastreo) {

        try {

            DetalleEnvio detalleEnvios = detalleEnvioRepository.findByNumeroDeRastreo(numRastreo).orElse(null);
            return DetalleEnvioMapper.INSTANCE.toDTO(detalleEnvios);

        } catch (Exception e) {

            log.error("Error obteniendo detalleEnvio por numRastreo", e);

        }

        return null;
    }

    public List<DetalleEnvioDTO> getDetalleEnvioByEstadoDelPedido(String direccion) {

        try{

            List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findByEstadoDelPedido(direccion).orElse(null);
            return detalleEnvios.stream().map(DetalleEnvioMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo detalleEnvio por direccion");

        }

        return List.of();
    }

    /*Create, update, delete */
    
    public DetalleEnvioDTO createDetalleEnvio(DetalleEnvioDTO detalleEnvioDTO){

        try {

            if(detalleEnvioDTO.idEnvio() != null) {
                throw new IllegalArgumentException("La id se generará mediante la base de datos");
            }

            DetalleEnvio detalleEnvio = DetalleEnvioMapper.INSTANCE.toEntity(detalleEnvioDTO);
            DetalleEnvio savedDetalleEnvio = detalleEnvioRepository.save(detalleEnvio);

            return DetalleEnvioMapper.INSTANCE.toDTO(savedDetalleEnvio);

        } catch (Exception e) {

            log.error("Error creando detalleEnvio", e);

        }

        return null;
    }

    public DetalleEnvioDTO updateDetalleEnvio(Long id, DetalleEnvioDTO detalleEnvioDTO) {

        try {

            if (id == null || id < 0) {

                throw new IllegalArgumentException("La id no puede ser nula o menor que cero.");

            }

            DetalleEnvio detalleEnvioAActualizar = detalleEnvioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("El envio no existe"));
            DetalleEnvio detalleEnvio = DetalleEnvioMapper.INSTANCE.toEntity(detalleEnvioDTO);
            
            detalleEnvio.setIdEnvio(detalleEnvioAActualizar.getIdEnvio());

            detalleEnvioAActualizar = detalleEnvioRepository.save(detalleEnvio);

            DetalleEnvio savedDetalleEnvio = detalleEnvioRepository.save(detalleEnvioAActualizar);
            return DetalleEnvioMapper.INSTANCE.toDTO(savedDetalleEnvio);

        } catch (Exception e) {

            log.error("Error actualizando al detalleEnvio");
            
        }

        return null;
    }

    public boolean deleteDetalleEnvio(Long id) {

        try {

            detalleEnvioRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error eliminando el detalleEnvio");

        }

        return false;
    }
}