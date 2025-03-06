package com.example.FarmaciaData.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.FarmaciaData.dto.ProductoDto;
import com.example.FarmaciaData.mapper.ProductoMapper;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.FacturaRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private FarmaciaRepository farmaciaRepository;
    @Autowired
    private FacturaRepository facturaRepository;



    public List<ProductoDto> listarProductos(){
        return productoRepository.findAll().stream().map(ProductoMapper::toDTO).toList();
    }

    public ProductoDto obtenerPorCodigoBarras(String codigoBarras) {
        Producto producto = productoRepository.findByCodigoBarras(codigoBarras);
        if (producto == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }
        return ProductoMapper.toDTO(producto);
    }

    public List<ProductoDto> obtenerPorNombre(List<String> nombres){
        List<Producto> productos = productoRepository.findByNombreIn(nombres);
            if (productos == null) {
                throw new RuntimeException("Producto no encontrado");
            }
            return ProductoMapper.toDTO(productos);
    }

    public ProductoDto crearProducto(ProductoDto productoDto){
        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
            .filter(cat->productoDto.getFarmacias().contains(cat.getNombre()))
            .toList();
        
        Producto producto = Producto.builder()
            .nombre(productoDto.getNombre())
            .precio(productoDto.getPrecio())
            .farmacias(farmacias)
            .build();
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }

    public ProductoDto actualizarProducto(String codigoBarras, ProductoDto productoDto) {
        Producto producto = productoRepository.findByCodigoBarras(codigoBarras);
            if (producto == null) {
                throw new EntityNotFoundException("Producto con código de barras " + codigoBarras + " no encontrado");
            }
    
        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
                .filter(cat -> productoDto.getFarmacias().stream()
                        .anyMatch(farmaciaDto -> farmaciaDto.equals(cat.getNombre())))  
                .collect(Collectors.toList());
    
        if (farmacias.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron farmacias válidas");
        }
    
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        producto.setFarmacias(farmacias);
        producto.setFechaUpdate(LocalDate.now());
    
        return ProductoMapper.toDTO(productoRepository.save(producto));
    }
    
    @Transactional
    public ProductoDto eliminarProducto(String codigoBarras) {
        Producto producto = productoRepository.findByCodigoBarras(codigoBarras);
        if (producto == null) {
            throw new EntityNotFoundException("Producto con código de barras " + codigoBarras + " no encontrado");
        }
        facturaRepository.deleteByProductoId(codigoBarras);
        productoRepository.deleteByCodigoBarras(codigoBarras);
        System.out.println("Producto eliminado");
        return ProductoMapper.toDTO(producto);
    }

}
