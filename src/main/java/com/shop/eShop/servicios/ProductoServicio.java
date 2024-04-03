package com.shop.eShop.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.eShop.dto.producto.ProductoDTO;
import com.shop.eShop.dto.producto.ProductoMapper;
import com.shop.eShop.entities.Producto;
import com.shop.eShop.repositories.ProductoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class ProductoServicio {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> getAllProductos() {

        try {

            List<Producto> productos = productoRepository.findAll();
            return productos.stream().map(ProductoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo todos los productos", e);

        }

        return List.of();

    }

    public ProductoDTO getProductoById(Long id) {

        try {

            Producto producto = productoRepository.findById(id).orElse(null);
            return ProductoMapper.INSTANCE.toDTO(producto);

        } catch (Exception e) {

            log.error("Error obteniendo producto por id",e);

        }

        return null;
    }

    public List<ProductoDTO> getProductoByNombre(String nombre) {

        try {

            List<Producto> productos = productoRepository.findByNombre(nombre).orElse(null);
            return productos.stream().map(ProductoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e){

            log.error("Error obteniendo producto por nombre", e);

        }

        return List.of();    
    }

    public List<ProductoDTO> getProductoByPrecioANDStock(Double precio, Integer stock) {

        try{

            List<Producto> productos = productoRepository.findByPrecioANDStock(precio, stock).orElse(null);
            return productos.stream().map(ProductoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo producto por precio y stock", e);

        }

        return List.of();
    }

    public List<ProductoDTO> getProductoByStockMayorQueCero() {

        try{

            List<Producto> productos = productoRepository.findByStock().orElse(null);
            return productos.stream().map(ProductoMapper.INSTANCE::toDTO).toList();

        } catch (Exception e) {

            log.error("Error obteniendo producto disponibles", e);

        }

        return List.of();
    }

    /*Create, update, delete */
    
    public ProductoDTO createProducto(ProductoDTO productoDTO){

        try {

            if(productoDTO.idProducto() != null) {
                throw new IllegalArgumentException("La id se generará mediante la DB");
            }

            Producto producto = ProductoMapper.INSTANCE.toEntity(productoDTO);
            Producto savedProducto = productoRepository.save(producto);

            return ProductoMapper.INSTANCE.toDTO(savedProducto);

        } catch (Exception e) {

            log.error("Error creando el producto", e);

        }

        return null;
    }

    public ProductoDTO updateProducto(Long id, ProductoDTO productoDTO) {

        try {

            if (id == null || id < 0) {

                throw new IllegalArgumentException("La id no puede ser nula o menor que cero.");

            }

            Producto productoAActualizar = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("El producto no existe"));
            Producto producto = ProductoMapper.INSTANCE.toEntity(productoDTO);
            
            producto.setIdProducto(productoAActualizar.getIdProducto());

            productoAActualizar = productoRepository.save(producto);

            Producto savedProducto = productoRepository.save(productoAActualizar);
            return ProductoMapper.INSTANCE.toDTO(savedProducto);

        } catch (Exception e) {

            log.error("Error actualizando el producto", e);
            
        }

        return null;
    }

    public boolean deleteProducto(Long id) {

        try {

            productoRepository.deleteById(id);
            return true;

        } catch (Exception e) {

            log.error("Error eliminando el producto", e);

        }

        return false;
    }
}
