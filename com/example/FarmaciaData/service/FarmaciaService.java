package com.example.FarmaciaData.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FarmaciaData.dto.FarmaciaDto;
import com.example.FarmaciaData.mapper.FarmaciaMapper;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

@Service
public class FarmaciaService {

    @Autowired
    private FarmaciaRepository farmaciaRepository;


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;



    public List<FarmaciaDto> listarFarmacias(){
        return farmaciaRepository.findAll().stream().map(FarmaciaMapper::toDTO).toList();
    }

        public FarmaciaDto crearFarmacia(FarmaciaDto farmaciaDto){
        List<Cliente> clientes = clienteRepository.findAll().stream()
            .filter(cat->farmaciaDto.getClientes().contains(cat.getNombre()))
            .toList();
        List<Producto> productos = productoRepository.findAll().stream()
            .filter(cat->farmaciaDto.getProductos().contains(cat.getNombre()))
            .toList();
        
        Farmacia farmacia = Farmacia.builder()
            .nombre(farmaciaDto.getNombre())
            .productos(productos)
            .clientes(clientes)
            .build();
        return FarmaciaMapper.toDTO(farmaciaRepository.save(farmacia));
    }


}
