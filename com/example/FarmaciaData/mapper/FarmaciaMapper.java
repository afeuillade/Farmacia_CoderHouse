package com.example.FarmaciaData.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FarmaciaData.dto.FarmaciaDto;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

@Component
public class FarmaciaMapper {

        public static FarmaciaDto toDTO(Farmacia farmacia) {
        if(farmacia == null) {
            return null;
        }
        return FarmaciaDto.builder()
                .id(farmacia.getId())
                .nombre(farmacia.getNombre())
                .productos(farmacia.getProductos() !=null
                ? farmacia.getProductos().stream().map(Producto::getNombre).toList()
                : List.of())
                .clientes(farmacia.getClientes() !=null
                ? farmacia.getClientes().stream().map(Cliente::getNombre).toList()
                : List.of())
                .build();
    }


    public static Farmacia toEntity(FarmaciaDto farmaciaDto, ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        List<Producto> productos = farmaciaDto.getProductos() != null
                ? productoRepository.findByNombreIn(farmaciaDto.getProductos())
                : List.of();
        List<Cliente> clientes = farmaciaDto.getClientes() != null
                ? clienteRepository.findByNombreIn(farmaciaDto.getClientes())
                : List.of();
    
        return Farmacia.builder()
                .id(farmaciaDto.getId())
                .nombre(farmaciaDto.getNombre())
                .productos(productos)
                .clientes(clientes)
                .build();
    }
    

}
