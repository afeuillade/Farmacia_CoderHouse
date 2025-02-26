package com.example.FarmaciaData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FarmaciaData.ApiResponse;
import com.example.FarmaciaData.dto.FarmaciaDto;
import com.example.FarmaciaData.service.FarmaciaService;

@RestController
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    
    @GetMapping("/farmacia/all")
    public ResponseEntity<List<FarmaciaDto>> listar() {
        return ResponseEntity.ok().body(farmaciaService.listarFarmacias());
    }

    @PostMapping("/agregarFarmacia")
    public ResponseEntity <ApiResponse<FarmaciaDto>> agregarCliente(@RequestBody FarmaciaDto farmaciaDto) {
        try {
            FarmaciaDto farmaciaCreada = farmaciaService.crearFarmacia(farmaciaDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", farmaciaCreada, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }


}
