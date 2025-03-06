package com.example.FarmaciaData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.FarmaciaData.ApiResponse;
import com.example.FarmaciaData.dto.ClienteDto;
import com.example.FarmaciaData.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/cliente/all")
    @Operation(summary = "Obtenemos todos los clientes que registramos")
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

    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok().body(clienteService.listarClientes());
    }

    @PostMapping("/agregarCliente")
    @Operation(summary ="Agregamos a los clientes que se registran")
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
    public ResponseEntity <ApiResponse<ClienteDto>> agregarCliente(@RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteCreado = clienteService.crearCliente(clienteDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", clienteCreado, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }

    @DeleteMapping("/cliente/eliminar/{dni}")
    @Operation(summary ="Eliminamos a los clientes que se encuentran registrados")
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
    public ResponseEntity<ApiResponse<String>> eliminarCliente(@PathVariable String dni) {
        try {
            clienteService.eliminarClientePorDni(dni);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", "Cliente eliminado", "Sin errores"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Error", null, e.getMessage()));
        }
    }

    @PutMapping("/cliente/modificar/{dni}")
    @Operation(summary ="Modificamos a los clientes que tengan errores", description= "Hay que enviar el dni y en el cuerpo del mensaje los datos a modificar. No se modifica el DNI")
        public ResponseEntity<ApiResponse<ClienteDto>> modificarCliente(
            @PathVariable String dni, 
            @RequestBody ClienteDto clienteDto
        ) {
            try {
                ClienteDto clienteModificado = clienteService.modificarCliente(dni, clienteDto);
                return ResponseEntity.ok().body(new ApiResponse<>("OK", clienteModificado, "Cliente modificado con éxito"));
            } catch (EntityNotFoundException e) {
                return ResponseEntity.badRequest().body(new ApiResponse<>("Error", null, e.getMessage()));
            }
    }


}
