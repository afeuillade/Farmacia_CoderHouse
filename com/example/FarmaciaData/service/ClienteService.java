package com.example.FarmaciaData.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FarmaciaData.dto.ClienteDto;
import com.example.FarmaciaData.mapper.ClienteMapper;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private ProductoRepository productoRepository;


    public List<ClienteDto> listarClientes(){
        return clienteRepository.findAll().stream().map(ClienteMapper::toDTO).toList();
    }

        public ClienteDto crearCliente(ClienteDto clienteDto){
        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
            .filter(farmacia->clienteDto.getFarmacias().contains(farmacia.getNombre()))
            .toList();
            List<Producto> productos = productoRepository.findAll().stream()
            .filter(producto->clienteDto.getProductos().contains(producto.getNombre()))
            .toList();
        
        Cliente cliente = Cliente.builder()
            .nombre(clienteDto.getNombre())
            .apellido(clienteDto.getApellido())
            .dni(clienteDto.getDni())
            .direccion(clienteDto.getDireccion())
            .telefono(clienteDto.getTelefono())
            .productos(productos)
            .farmacias(farmacias)
            .build();
        return ClienteMapper.toDTO(clienteRepository.save(cliente));
    }

    public void eliminarClientePorDni(String dni) {
        Cliente cliente = clienteRepository.findByDni(dni);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente con DNI " + dni + " no encontrado");
        }
        clienteRepository.delete(cliente);
    }

    public ClienteDto modificarCliente(String dni, ClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findByDni(dni);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente con DNI " + dni + " no encontrado");
        }
    
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setDireccion(clienteDto.getDireccion());
        cliente.setTelefono(clienteDto.getTelefono());
    
        
        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
        .filter(farmacia -> clienteDto.getFarmacias().contains(farmacia.getNombre()))
        .collect(Collectors.toList()); 

        List<Producto> productos = productoRepository.findAll().stream()
        .filter(producto -> clienteDto.getProductos().contains(producto.getNombre()))
        .collect(Collectors.toList()); 
    
        return ClienteMapper.toDTO(clienteRepository.save(cliente));
    }



}
