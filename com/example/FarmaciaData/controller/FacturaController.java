package com.example.FarmaciaData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FarmaciaData.dto.FacturaDto;
import com.example.FarmaciaData.service.FacturaService;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    // Listar todas las facturas
    @GetMapping
    public ResponseEntity<List<FacturaDto>> listarFacturas() {
        List<FacturaDto> facturas = facturaService.listarFacturas();
        return ResponseEntity.ok(facturas);
    }

    // Crear una nueva factura
    @PostMapping("/crearFactura")
    public ResponseEntity<FacturaDto> crearFactura(@RequestBody FacturaDto facturaDto) {
        FacturaDto facturaCreada = facturaService.crearFactura(facturaDto);
        return ResponseEntity.status(201).body(facturaCreada);
    }

    // Obtener una factura por su ID
    @GetMapping("/{id}")
    public ResponseEntity<FacturaDto> obtenerFactura(@PathVariable Long id) {
        FacturaDto facturaDto = facturaService.obtenerFactura(id);
        if (facturaDto != null) {
            return ResponseEntity.ok(facturaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una factura por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

}
