package com.example.FarmaciaData.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.FarmaciaData.dto.FacturaDto;
import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Factura;
import com.example.FarmaciaData.models.Farmacia;
import com.example.FarmaciaData.models.Producto;
import com.example.FarmaciaData.repository.ClienteRepository;
import com.example.FarmaciaData.repository.FarmaciaRepository;
import com.example.FarmaciaData.repository.ProductoRepository;

@Component
public class FacturaMapper {

    public static FacturaDto toDTO(Factura factura) {
        if (factura == null) {
            return null;
        }

        return FacturaDto.builder()
            .id(factura.getId())
            .numeroFactura(factura.getNumeroFactura())
            .fecha(factura.getFecha())
            .clienteId(factura.getCliente().getId())  // Mapeo del cliente
            .farmaciaIds(factura.getFarmacias().stream().map(Farmacia::getId).toList())  // Mapeo de farmacias
            .productoCodigoBarras(factura.getProductos().stream().map(Producto::getCodigoBarras).toList())  // Mapeo de productos
            .build();
    }

    public static Factura toEntity(FacturaDto facturaDto, ClienteRepository clienteRepository, FarmaciaRepository farmaciaRepository, ProductoRepository productoRepository) {
        Cliente cliente = clienteRepository.findById(facturaDto.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        List<Farmacia> farmacias = farmaciaRepository.findAll().stream()
            .filter(farmacia -> facturaDto.getFarmaciaIds().contains(farmacia.getId()))
            .toList();
        List<Producto> productos = productoRepository.findAll().stream()
            .filter(producto -> facturaDto.getProductoCodigoBarras().contains(producto.getCodigoBarras()))
            .toList();

        return Factura.builder()
            .numeroFactura(facturaDto.getNumeroFactura())
            .fecha(facturaDto.getFecha())
            .cliente(cliente)
            .productos(productos)
            .farmacias(farmacias)
            .build();
    }

}
