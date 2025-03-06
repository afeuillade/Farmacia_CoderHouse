package com.example.FarmaciaData.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "productos")
public class Producto {

    @Id
    @Column (name = "codigo_barras", unique = true, nullable =false, length =10)
    private String codigoBarras;

    @Column(name = "NAME")
    private String nombre;
    private Double precio;
    private Integer stock;

    
    private LocalDate fechaCreacion;
    private LocalDate fechaUpdate;
    private LocalDate fechaDelete;

    @ManyToMany
    @JoinTable(name = "productos_farmacias", joinColumns = @JoinColumn(name = "codigo_barras"), inverseJoinColumns = @JoinColumn(name = "farmacia_id"))
    private List<Farmacia> farmacias;



    @ManyToMany(mappedBy = "productos") private List<Cliente> clientes;

    @ManyToMany(mappedBy = "productos")
    private List<Factura> facturas;

   
    // Método para reducir el stock
    public void reducirStock(int cantidad) {
        if (this.stock >= cantidad) {
            this.stock -= cantidad;
        } else {
            throw new RuntimeException("Stock insuficiente para el producto: " + this.nombre);
        }
    }




    // Generar código de barras aleatorio automáticamente
    @PrePersist
    public void generarCodigoBarras() {
        if (codigoBarras == null || codigoBarras.isEmpty()) {
            this.codigoBarras = generarCodigoAleatorio();
        }
        this.fechaCreacion = LocalDate.now();
    }

    // Método para generar un código de barras aleatorio de 10 dígitos
    private String generarCodigoAleatorio() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            codigo.append(random.nextInt(10)); 
        }
        return codigo.toString();
    }
}

