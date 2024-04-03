package com.shop.eShop.servicios;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.eShop.dto.cliente.ClienteDTO;
import com.shop.eShop.dto.cliente.ClienteMapper;
import com.shop.eShop.dto.pedido.PedidoDTO;
import com.shop.eShop.dto.pedido.PedidoMapper;
import com.shop.eShop.entities.Cliente;
import com.shop.eShop.entities.Pedido;
import com.shop.eShop.repositories.ClienteRepository;
import com.shop.eShop.repositories.PedidoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PedidoServicio {
    /* C-> CREATE R-> READ U-> UPDATE D-> DELETE */
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    // READ ALL
    public List<PedidoDTO> getAllPedidos() {

        try {

            List<Pedido> pedidos = pedidoRepository.findAll();
            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los pedidos", e);

        }

        return List.of();
    }

    // READ ID
    public PedidoDTO getPedidoById(Long id) {

        try {

            if (id==null) {

                throw new IllegalArgumentException("La id no puede ser nula");

            }

            Pedido pedido = pedidoRepository.findById(id).orElse(null);
            return PedidoMapper.INSTANCE.toDTO(pedido);

        } catch (Exception e) {

            log.error("Error obteniendo el pedido por su id", e);

        }

        return null;
    }

    // CREATE
    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {

        try {

            if (pedidoDTO.idPedido() != null) {

                throw new IllegalArgumentException("La id se va a generar en la base de datos");

            }

            Pedido pedido = PedidoMapper.INSTANCE.toEntity(pedidoDTO);

            if (pedido == null) {

                throw new IllegalArgumentException("El pedido no puede ser nulo");

            }

            pedido = pedidoRepository.save(pedido);
            return PedidoMapper.INSTANCE.toDTO(pedido);

        } catch (Exception e) {

            log.error("Error creando el pedido", e);

        }

        return null;
    }

    // UPDATE
    public PedidoDTO updatePedido(Long id, PedidoDTO pedidoDTO) {

        try {

            if (id == null) {

                throw new IllegalArgumentException("La id no puede ser nula");

            }

            Pedido pedidoAActualizar = pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado"));
            Pedido pedido = PedidoMapper.INSTANCE.toEntity(pedidoDTO);

            pedido.setIdPedido(pedidoAActualizar.getIdPedido());
            pedidoAActualizar = pedidoRepository.save(pedido);
            Pedido savedPedido = pedidoRepository.save(pedidoAActualizar);

            return PedidoMapper.INSTANCE.toDTO(savedPedido);

        } catch (Exception e) {

            log.error("Error actualizando el pedido", e);

        }

        return null;
    }

    // DELETE
    public boolean deletePedido(Long id) {

        try {

            pedidoRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error borrando el pedido", e);

        }
        return false;
    }

    /* Métodos del repository */
    /* Retrieve pedidos within a date range */
    public List<PedidoDTO> findByFecha(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pedido> pedidos = pedidoRepository.findByFecha(ts).orElse(null);

            if (pedidos == null) {

                throw new IllegalArgumentException("No hubo pedidos encontrados");

            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pedidos por fecha", e);

        }

        return List.of();
    }

    public List<PedidoDTO> findByFechaAfter(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pedido> pedidos = pedidoRepository.findByFechaAfter(ts).orElse(null);

            if (pedidos == null) {
                throw new IllegalArgumentException("No hubo pedidos encontrados");
            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pedidos antes de la fecha fecha", e);

        }
        
        return List.of();
    }

    public List<PedidoDTO> findByFechaBefore(String fecha) {
        try {

            Timestamp ts = Timestamp.valueOf(fecha); // yyyy-mm-dd hh:mm:ss

            List<Pedido> pedidos = pedidoRepository.findByFechaBefore(ts).orElse(null);

            if (pedidos == null) {
                throw new IllegalArgumentException("No hubo pedidos encontrados");
            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pedidos despues de la fecha", e);

        }
        
        return List.of();
    }

    public List<PedidoDTO> findByFechaBetween(String fecha1, String fecha2) {
        try {

            Timestamp ts1 = Timestamp.valueOf(fecha1); // yyyy-mm-dd hh:mm:ss
            Timestamp ts2 = Timestamp.valueOf(fecha2); // yyyy-mm-dd hh:mm:ss

            List<Pedido> pedidos = pedidoRepository.findByFechaBetween(ts1, ts2).orElse(null);

            if (pedidos == null) {
                throw new IllegalArgumentException("No hubo pedidos encontrados");
            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();
        } catch (Exception e) {

            log.error("Error obteniendo pedidos entra las fechas", e);

        }
        
        return List.of();
    }

    /* Retrieve pedidos by an order identifier and pedido method */
    public List<PedidoDTO> findByCliente(Long id) {
        try {

            if (id == null || id < 0) {
                throw new IllegalArgumentException("El ID del pedido no puede ser nulo");
            }

            Cliente cliente = clienteRepository.findById(id).orElse(null);

            if (cliente == null) {

                throw new IllegalArgumentException("El cliente no existe");

            }

            List<Pedido> pedidos = pedidoRepository.findByCliente(cliente).orElse(null);

            if (pedidos == null) {

                throw new IllegalArgumentException("Pedido no encontrado");

            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo los pedidos por id del pedido y el metodo de pedido", e);

        }

        return List.of();
    }

    public List<PedidoDTO> findByClienteAndEstado(Long id, String estado) {
        try {

            if (id == null || id < 0) {
                throw new IllegalArgumentException("El ID del pedido no puede ser nulo");
            }

            Cliente cliente = clienteRepository.findById(id).orElse(null);

            if (cliente == null) {

                throw new IllegalArgumentException("El cliente no existe");

            }

            List<Pedido> pedidos = pedidoRepository.findByClienteAndEstado(cliente, estado).orElse(null);

            if (pedidos == null) {

                throw new IllegalArgumentException("No hubo pedidos encontrados");

            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo los pedidos por el metodo de pedido", e);

        }

        return List.of();
    }

    public List<PedidoDTO> findByEstado(String estado) {
        try {

            List<Pedido> pedidos = pedidoRepository.findByEstado(estado).orElse(null);

            if (pedidos == null) {

                throw new IllegalArgumentException("No hubo pedidos encontrados");

            }

            return pedidos.stream().map(PedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo los pedidos por el metodo de pedido", e);

        }

        return List.of();
    }
}
