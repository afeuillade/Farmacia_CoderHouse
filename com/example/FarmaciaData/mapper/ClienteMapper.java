package com.example.FarmaciaData.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FarmaciaData.dto.ClienteDto;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

@Component
public class ClienteMapper {

        public static ClienteDto toDTO(Cliente cliente) {
        if(cliente == null) {
            return null;
        }
        return ClienteDto.builder()
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .dni(cliente.getDni())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())                
                .productos(cliente.getProductos() !=null
                ? cliente.getProductos().stream().map(Producto::getCodigoBarras).toList()
                : List.of())
                .farmacias(cliente.getFarmacias() !=null
                ? cliente.getFarmacias().stream().map(Farmacia::getNombre).toList()
                : List.of())


                .build();
    }


    public static Cliente toEntity(ClienteDto clienteDto, FarmaciaRepository farmaciaRepository, ProductoRepository productoRepository) {
        return Cliente.builder()
                .nombre(clienteDto.getNombre())
                .productos(clienteDto.getProductos() !=null
                ? productoRepository.findAll().stream()
                    .filter(cat->clienteDto.getProductos().contains(cat.getNombre()))
                    .toList()
                : List.of())
                .farmacias(clienteDto.getFarmacias() !=null
                    ? farmaciaRepository.findAll().stream()
                        .filter(cat->clienteDto.getFarmacias().contains(cat.getNombre()))
                        .toList()
                    : List.of())

                .build();
    }



}
