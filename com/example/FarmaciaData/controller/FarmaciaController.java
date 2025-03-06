package com.example.FarmaciaData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FarmaciaData.ApiResponse;
import com.example.FarmaciaData.dto.ClienteDto;
import com.example.FarmaciaData.dto.FarmaciaDto;
import com.example.FarmaciaData.service.FarmaciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class FarmaciaController {

    @Autowired
    private FarmaciaService farmaciaService;

    
    @GetMapping("/farmacia/all")
    @Operation(summary = "Obtenemos todas las farmacias que registramos")
    public ResponseEntity<List<FarmaciaDto>> listar() {
        return ResponseEntity.ok().body(farmaciaService.listarFarmacias());
    }

    @PostMapping("/agregarFarmacia")
    @Operation(summary = "Agregamos a las farmacias que se registran")
    @ApiResponses(value={
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "Successful opereation", 
            content = @Content(schema = @Schema (implementation = ClienteDto.class))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404", 
            description = "Erro Fatal", 
            content = @Content(schema = @Schema (implementation = ApiResponse.class)))
    })
    public ResponseEntity <ApiResponse<FarmaciaDto>> agregarCliente(@RequestBody FarmaciaDto farmaciaDto) {
        try {
            FarmaciaDto farmaciaCreada = farmaciaService.crearFarmacia(farmaciaDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", farmaciaCreada, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }


}
