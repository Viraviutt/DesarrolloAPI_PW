package com.shop.eShop.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.eShop.dto.pago.PagoDTO;
import com.shop.eShop.dto.pago.PagoMapper;
import com.shop.eShop.entities.Pago;
import com.shop.eShop.repositories.PagoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class PagoServicio {
    /* C-> CREATE R-> READ U-> UPDATE D-> DELETE */
    @Autowired
    private PagoRepository pagoRepository;
    // READ ALL
    public List<PagoDTO> getAllPagos() {

        try {

            List<Pago> pagos = pagoRepository.findAll();
            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los pagos", e);

        }

        return List.of();
    }

    // READ ID
    public PagoDTO getPagoById(Long id) {

        try {

            if (id==null) {

                throw new IllegalArgumentException("La id no puede ser nula");

            }

            Pago pago = pagoRepository.findById(id).orElse(null);
            return PagoMapper.INSTANCE.toDTO(pago);

        } catch (Exception e) {

            log.error("Error obteniendo el pago por su id", e);

        }

        return null;
    }

    // CREATE
    public PagoDTO createPago(PagoDTO pagoDTO) {

        try {

            if (pagoDTO.idPago() != null) {

                throw new IllegalArgumentException("La id se va a generar en la base de datos");

            }

            Pago pago = PagoMapper.INSTANCE.toEntity(pagoDTO);

            if (pago == null) {

                throw new IllegalArgumentException("El pago no puede ser nulo");

            }

            pago = pagoRepository.save(pago);
            return PagoMapper.INSTANCE.toDTO(pago);

        } catch (Exception e) {

            log.error("Error creando el pago", e);

        }

        return null;
    }

    // UPDATE
    public PagoDTO updatePago(Long id, PagoDTO pagoDTO) {

        try {

            if (id == null) {

                throw new IllegalArgumentException("La id no puede ser nula");

            }

            Pago pagoAActualizar = pagoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
            Pago pago = PagoMapper.INSTANCE.toEntity(pagoDTO);

            pago.setIdPago(pagoAActualizar.getIdPago());
            pagoAActualizar = pagoRepository.save(pago);
            Pago savedPago = pagoRepository.save(pagoAActualizar);

            return PagoMapper.INSTANCE.toDTO(savedPago);

        } catch (Exception e) {

            log.error("Error actualizando el pago", e);

        }

        return null;
    }

    // DELETE
    public boolean deletePago(Long id) {

        try {

            pagoRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error borrando el pago", e);

        }
        return false;
    }

    /* Métodos del repository */
    /* Retrieve pagos within a date range */
    public List<PagoDTO> findByFecha(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pago> pagos = pagoRepository.findByFecha(ts).orElse(null);

            if (pagos == null) {

                throw new IllegalArgumentException("No hubo pagos encontrados");

            }

            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pagos por fecha", e);

        }

        return List.of();
    }

    public List<PagoDTO> findByFechaAfter(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pago> pagos = pagoRepository.findByFechaAfter(ts).orElse(null);

            if (pagos == null) {
                throw new IllegalArgumentException("No hubo pagos encontrados");
            }

            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pagos antes de la fecha fecha", e);

        }
        
        return List.of();
    }

    public List<PagoDTO> findByFechaBefore(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pago> pagos = pagoRepository.findByFechaBefore(ts).orElse(null);

            if (pagos == null) {
                throw new IllegalArgumentException("No hubo pagos encontrados");
            }

            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pagos despues de la fecha", e);

        }
        
        return List.of();
    }

    public List<PagoDTO> findByFechaBetween(String fecha1, String fecha2) {
        try {

            Timestamp ts1 = Timestamp.valueOf(fecha1); // yyyy-mm-dd hh:mm:ss
            Timestamp ts2 = Timestamp.valueOf(fecha2); // yyyy-mm-dd hh:mm:ss

            List<Pago> pagos = pagoRepository.findByFechaBetween(ts1, ts2).orElse(null);

            if (pagos == null) {
                throw new IllegalArgumentException("No hubo pagos encontrados");
            }

            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pagos entra las fechas", e);

        }
        
        return List.of();
    }

    /* Retrieve pagos by an order identifier and pago method */
    public PagoDTO findByIdPedidoANDMetodoPago(Long idPedido, String metodoPago) {
        try {
            if (idPedido == null || idPedido < 0) {
                throw new IllegalArgumentException("El ID del pedido no puede ser nulo");
            }
            Pago pagos = pagoRepository.findByIdPedidoAndMetodoPago(idPedido, metodoPago).orElse(null);

            if (pagos == null) {

                throw new IllegalArgumentException("Pago no encontrado");

            }

            return PagoMapper.INSTANCE.toDTO(pagos);

        } catch (Exception e) {

            log.error("Error obteniendo los pagos por id del pedido y el metodo de pago", e);

        }
        return null;
    }

    public List<PagoDTO> findByMetodoPago(String metodoPago) {
        try {

            List<Pago> pagos = pagoRepository.findByMetodoPago(metodoPago).orElse(null);

            if (pagos == null) {

                throw new IllegalArgumentException("No hubo pagos encontrados");

            }

            return pagos.stream().map(PagoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo los pagos por el metodo de pago", e);

        }

        return null;
    }
}
