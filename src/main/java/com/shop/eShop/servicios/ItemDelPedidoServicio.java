package com.shop.eShop.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.eShop.dto.itemDelPedido.ItemDelPedidoDTO;
import com.shop.eShop.dto.itemDelPedido.ItemDelPedidoMapper;
import com.shop.eShop.entities.ItemDelPedido;
import com.shop.eShop.repositories.ItemDelPedidoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class ItemDelPedidoServicio {

    @Autowired
    private ItemDelPedidoRepository itemDelPedidoRepository;

    public List<ItemDelPedidoDTO> getAllItemsDelPedido() {

        try {

            List<ItemDelPedido> itemsDelPedido = itemDelPedidoRepository.findAll();
            return itemsDelPedido.stream().map(ItemDelPedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los itemDelPedidos", e);

        }

        return List.of();

    }

    public ItemDelPedidoDTO getItemDelPedidoById(Long id) {

        try {

            ItemDelPedido itemDelPedido = itemDelPedidoRepository.findById(id).orElse(null);
            return ItemDelPedidoMapper.INSTANCE.toDTO(itemDelPedido);

        } catch (Exception e) {

            log.error("Error obteniendo itemDelPedido por id",e);

        }

        return null;
    }

        public List<ItemDelPedidoDTO> getItemDelPedidoByIdPedido(Long id) {

        try {

            List<ItemDelPedido> itemDelPedidos = itemDelPedidoRepository.findByIdDelPedido(id).orElse(null);
            return itemDelPedidos.stream().map(ItemDelPedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e){

            log.error("Error obteniendo itemDelPedido por transportadora", e);

        }

        return List.of();
    }

    public List<ItemDelPedidoDTO> getItemDelPedidoByIdProducto(Long id) {

        try {

            List<ItemDelPedido> itemDelPedidos = itemDelPedidoRepository.findByIdDelProducto(id).orElse(null);
            return itemDelPedidos.stream().map(ItemDelPedidoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e){

            log.error("Error obteniendo itemDelPedido por transportadora", e);

        }

        return List.of();
    }

    public Double getSumaTotalDeVentas(Long id) {
        try {

            return itemDelPedidoRepository.calcSumForTotalDeVentas(id).orElse(null);
            
        } catch (Exception e) {

            log.error("Error obteniendo el total de la suma de las ventas", e);

        }

        return null;
    }

    /*Create, update, delete */

    public ItemDelPedidoDTO createItemDelPedido(ItemDelPedidoDTO itemDelPedidoDTO){

        try {

            if(itemDelPedidoDTO.idItemsDelPedido() != null) {
                throw new IllegalArgumentException("La id se generará mediante la DB");
            }

            ItemDelPedido itemDelPedido = ItemDelPedidoMapper.INSTANCE.toEntity(itemDelPedidoDTO);
            ItemDelPedido savedItemDelPedido = itemDelPedidoRepository.save(itemDelPedido);

            return ItemDelPedidoMapper.INSTANCE.toDTO(savedItemDelPedido);

        } catch (Exception e) {

            log.error("Error creando itemDelPedido", e);

        }

        return null;
    }

    public ItemDelPedidoDTO updateItemDelPedido(Long id, ItemDelPedidoDTO itemDelPedidoDTO) {

        try {

            if (id == null || id < 0) {

                throw new IllegalArgumentException("La id no puede ser nula o menor que cero.");

            }

            ItemDelPedido itemDelPedidoAActualizar = itemDelPedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("El itemDelPedido no existe"));
            ItemDelPedido itemDelPedido = ItemDelPedidoMapper.INSTANCE.toEntity(itemDelPedidoDTO);
            
            itemDelPedido.setIdItemsDelPedido(itemDelPedidoAActualizar.getIdItemsDelPedido());

            itemDelPedidoAActualizar = itemDelPedidoRepository.save(itemDelPedido);

            ItemDelPedido savedItemDelPedido = itemDelPedidoRepository.save(itemDelPedidoAActualizar);
            return ItemDelPedidoMapper.INSTANCE.toDTO(savedItemDelPedido);

        } catch (Exception e) {

            log.error("Error actualizando al itemDelPedido");
            
        }

        return null;
    }

    public boolean deleteItemDelPedido(Long id) {

        try {

            itemDelPedidoRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error eliminando el itemDelPedido");

        }

        return false;
    }
}
