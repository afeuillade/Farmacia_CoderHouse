package com.example.FarmaciaData.dto;

import java.util.List;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FarmaciaDto {
    private Long id;
    private String nombre;
    private List<String> productos;
    private List<String> clientes;
}
