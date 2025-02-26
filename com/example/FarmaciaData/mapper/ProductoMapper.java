package com.example.FarmaciaData.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.FarmaciaData.dto.ProductoDto;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.FarmaciaRepository;

@Component
public class ProductoMapper {

    public static ProductoDto toDTO(Producto producto) {
        if(producto == null) {
            return null;
        }

        return ProductoDto.builder()
                .codigoBarras(producto.getCodigoBarras())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .farmacias(producto.getFarmacias() !=null
                ? producto.getFarmacias().stream().map(Farmacia::getNombre).toList()
                : List.of())
                .build();
    }
    public static List<ProductoDto> toDTO(List<Producto> productos) {
        if (productos == null || productos.isEmpty()) {
            return List.of();
        }
        
        return productos.stream()
                        .map(ProductoMapper::toDTO)  
                        .collect(Collectors.toList());
    }


    public static Producto toEntity(ProductoDto productoDto, FarmaciaRepository farmaciaRepository) {
        return Producto.builder()
                .codigoBarras(productoDto.getCodigoBarras())
                .nombre(productoDto.getNombre())
                .precio(productoDto.getPrecio())
                .farmacias(productoDto.getFarmacias() !=null
                    ? farmaciaRepository.findAll().stream()
                        .filter(cat->productoDto.getFarmacias().contains(cat.getNombre()))
                        .toList()
                    : List.of())
                .build();
    }

}

