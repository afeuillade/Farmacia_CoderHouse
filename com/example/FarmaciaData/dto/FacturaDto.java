package com.example.FarmaciaData.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacturaDto {
    private Long id;
    private String numeroFactura;
    private LocalDate fecha;
    private Long clienteId;
    private List<Long> farmaciaIds;
    private List<String> productoCodigoBarras;
    private Double totalCalculado;

}
