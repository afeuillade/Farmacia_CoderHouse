package com.example.FarmaciaData.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDto {
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private List<String> productos;
    private List<String> farmacias;

}
