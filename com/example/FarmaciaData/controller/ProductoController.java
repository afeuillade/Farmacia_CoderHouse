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
import com.example.FarmaciaData.dto.ProductoDto;
import com.example.FarmaciaData.service.ProductoService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping("/producto/all")
    public ResponseEntity<List<ProductoDto>> listar() {
        return ResponseEntity.ok().body(productoService.listarProductos());
    }

    @GetMapping("/codigoBarras/{codigoBarras}")
    public ResponseEntity<ProductoDto> listar(@PathVariable String codigoBarras) {
        return ResponseEntity.ok().body(productoService.obtenerPorCodigoBarras(codigoBarras));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ProductoDto>> listarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok().body(productoService.obtenerPorNombre(List.of(nombre)));
    }

    @GetMapping("/apiResponse")
    public ResponseEntity<?> listarApiResponse(){
        List <ProductoDto> productos = productoService.listarProductos();
        return ResponseEntity.ok().body(new ApiResponse<>("ok",productos,"sin errores"));
    }

    @PutMapping("/modificar/{codigoBarras}")
    public ResponseEntity<?> modificar(@PathVariable String codigoBarras, @RequestBody ProductoDto productoDto) {
    try {
        ProductoDto productoActualizado = productoService.actualizarProducto(codigoBarras, productoDto);
        return ResponseEntity.ok().body(new ApiResponse<>("OK", productoActualizado, "Sin errores"));
    } catch (Exception e) {
         return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
    }
    }

    @PostMapping("/agregarProducto")
    public ResponseEntity <ApiResponse<ProductoDto>> agregarProducto(@RequestBody ProductoDto productoDto) {
        try {
            ProductoDto productoCreado = productoService.crearProducto(productoDto);
            return ResponseEntity.ok().body(new ApiResponse<>("OK", productoCreado, "Sin errores"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("Datos inválidos", null, e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{codigoBarras}")
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
