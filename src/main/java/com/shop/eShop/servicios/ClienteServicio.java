package com.shop.eShop.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.eShop.dto.cliente.ClienteDTO;
import com.shop.eShop.dto.cliente.ClienteMapper;
import com.shop.eShop.entities.Cliente;
import com.shop.eShop.repositories.ClienteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteDTO> getAllClientes() {

        try {

            List<Cliente> clientes = clienteRepository.findAll();
            return clientes.stream().map(ClienteMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los clientes", e);

        }

        return List.of();

    }

    public ClienteDTO getClienteById(Long id) {

        try {

            Cliente cliente = clienteRepository.findById(id).orElse(null);
            return ClienteMapper.INSTANCE.toDTO(cliente);

        } catch (Exception e) {

            log.error("Error obteniendo cliente por id",e);

        }

        return null;
    }

    public List<ClienteDTO> getClienteByNombre(String nombre) {

        try {

            List<Cliente> clientes = clienteRepository.findByNombre(nombre);
            return clientes.stream().map(ClienteMapper.INSTANCE::toDTO).toList();

        } catch (Exception e){

            log.error("Error obteniendo cliente por nombre", e);

        }

        return List.of();    
    }

    public List<ClienteDTO> getClienteByEmail(String email) {

        try {

            List<Cliente> clientes = clienteRepository.findByCorreo(email);
            return clientes.stream().map(ClienteMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo cliente por email", e);

        }

        return null;
    }

    public List<ClienteDTO> getClienteByDireccion(String direccion) {

        try{

            List<Cliente> clientes = clienteRepository.findByDireccion(direccion);
            return clientes.stream().map(ClienteMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo cliente por direccion");

        }

        return List.of();
    }

    /*Create, update, delete */
    
    public ClienteDTO createCliente(ClienteDTO clienteDTO){

        try {

            if(clienteDTO.idCliente() != null) {
                throw new IllegalArgumentException("La id se generará mediante la DB");
            }

            Cliente cliente = ClienteMapper.INSTANCE.toEntity(clienteDTO);
            Cliente savedCliente = clienteRepository.save(cliente);

            return ClienteMapper.INSTANCE.toDTO(savedCliente);

        } catch (Exception e) {

            log.error("Error creando cliente", e);

        }

        return null;
    }

    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {

        try {

            if (id == null || id < 0) {

                throw new IllegalArgumentException("La id no puede ser nula o menor que cero.");

            }

            Cliente clienteAActualizar = clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("El cliente no existe"));
            Cliente cliente = ClienteMapper.INSTANCE.toEntity(clienteDTO);
            clienteAActualizar = clienteAActualizar.updateOnlyNecesary(cliente);

            clienteRepository.save(null);
            Cliente savedCliente = clienteRepository.save(clienteAActualizar);
            return ClienteMapper.INSTANCE.toDTO(savedCliente);

        } catch (Exception e) {

            log.error("Error actualizando al cliente");
            
        }

        return null;
    }

    public boolean deleteCliente(Long id) {

        try {

            clienteRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error eliminando el cliente");

        }

        return false;
    }
}






