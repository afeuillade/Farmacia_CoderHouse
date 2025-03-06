package com.example.FarmaciaData.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.FarmaciaData.dto.ProductoDto;
import com.example.FarmaciaData.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;

@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping("/producto/all")
    @Operation(summary = "Obtenemos todos los productos que registramos")
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
    public ResponseEntity<List<ProductoDto>> listar() {
        return ResponseEntity.ok().body(productoService.listarProductos());
    }

    @GetMapping("/codigoBarras/{codigoBarras}")
    @Operation(summary = "Obtenemos un producto por su codigo de barras")
    public ResponseEntity<ProductoDto> listar(@PathVariable String codigoBarras) {
        return ResponseEntity.ok().body(productoService.obtenerPorCodigoBarras(codigoBarras));
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Obtenemos un producto por su nombre")
    public ResponseEntity<List<ProductoDto>> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok().body(productoService.obtenerPorNombre(List.of(nombre)));
    }

    @GetMapping("/apiResponse")
    @Operation(summary = "Obtenemos todos los productos que registramos con ApiResponse")
    public ResponseEntity<?> listarApiResponse(){
        List <ProductoDto> productos = productoService.listarProductos();
        return ResponseEntity.ok().body(new ApiResponse<>("ok",productos,"sin errores"));
    }

    @PutMapping("/modificar/{codigoBarras}")
    @Operation(summary = "Modificamos a los productos que tengan errores")
    public ResponseEntity<?> modificar(@PathVariable String codigoBarras, @RequestBody ProductoDto productoDto) {
    try {
        ProductoDto productoActualizado = productoService.actualizarProducto(codigoBarras, productoDto);
        return ResponseEntity.ok().body(new ApiResponse<>("OK", productoActualizado, "Sin errores"));
    } catch (Exception e) {
         return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
    }
    }

    @PostMapping("/agregarProducto")
    @Operation(summary ="Agregamos a los productos que se registran")
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
    public ResponseEntity <ApiResponse<ProductoDto>> agregarProducto(@RequestBody ProductoDto productoDto) {
        try {
            ProductoDto productoCreado = productoService.crearProducto(productoDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", productoCreado, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{codigoBarras}")
    @Operation(summary = "Eliminamos a los productos que se encuentran registrados")
    public ResponseEntity<ApiResponse<ProductoDto>> eliminaProducto(@PathVariable String codigoBarras){
        try {
            ProductoDto productoEliminado = productoService.eliminarProducto(codigoBarras);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", productoEliminado, "Sin errores"));
            } catch (EntityNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>("Error", null, e.getMessage()));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Error", null, "Error interno"));
            }
    }

}