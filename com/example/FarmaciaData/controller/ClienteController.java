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

import jakarta.persistence.EntityNotFoundException;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente/all")
    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok().body(clienteService.listarClientes());
    }

    @PostMapping("/agregarCliente")
    public ResponseEntity <ApiResponse<ClienteDto>> agregarCliente(@RequestBody ClienteDto clienteDto) {
        try {
            ClienteDto clienteCreado = clienteService.crearCliente(clienteDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", clienteCreado, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }

    @DeleteMapping("/cliente/eliminar/{dni}")
    public ResponseEntity<ApiResponse<String>> eliminarCliente(@PathVariable String dni) {
        try {
            clienteService.eliminarClientePorDni(dni);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", "Cliente eliminado", "Sin errores"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Error", null, e.getMessage()));
        }
    }

    @PutMapping("/cliente/modificar/{dni}")
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
