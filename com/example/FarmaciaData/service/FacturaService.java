package com.example.FarmaciaData.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FarmaciaData.dto.FacturaDto;
import com.example.FarmaciaData.mapper.FacturaMapper;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Factura;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.FacturaRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FarmaciaRepository farmaciaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public List<FacturaDto> listarFacturas() {
        return facturaRepository.findAll().stream().map(FacturaMapper::toDTO).toList();
    }

    public FacturaDto crearFactura(FacturaDto facturaDto) {
        Cliente cliente = clienteRepository.findById(facturaDto.getClienteId())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
            .filter(farmacia -> facturaDto.getFarmaciaIds().contains(farmacia.getId()))
            .toList();

        List<Producto> productos = productoRepository.findAll().stream()
            .filter(producto -> facturaDto.getProductoCodigoBarras().contains(producto.getCodigoBarras()))
            .toList();

        Factura factura = Factura.builder()
            .numeroFactura(facturaDto.getNumeroFactura())
            .fecha(facturaDto.getFecha())
            .cliente(cliente)
            .productos(productos)
            .farmacias(farmacias)
            .build();

        return FacturaMapper.toDTO(facturaRepository.save(factura));
    }
    public FacturaDto obtenerFactura(Long id) {
        Factura factura = facturaRepository.findById(id).orElse(null); 
        
        if (factura != null) {
            return FacturaMapper.toDTO(factura);  
        } else {
            return null;  
        }
    }


    public void eliminarFactura(Long id) {
        Factura factura = facturaRepository.findById(id).orElse(null); 
        
        if (factura != null) {
            facturaRepository.delete(factura);  
        } else {
            throw new RuntimeException("Factura no encontrada");  
        }
    }


    


}
