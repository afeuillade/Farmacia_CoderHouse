package com.example.FarmaciaData.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDto {
    private String codigoBarras;
    private String nombre;
    private Double precio;  
    private Integer stock;
    private List<String> farmacias;
  

}
