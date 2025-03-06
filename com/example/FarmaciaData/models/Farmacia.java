package com.example.FarmaciaData.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "farmacias")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Farmacia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="farmacia_id")
    private Long id;
    @Column(name="farmacia_nombre")
    private String nombre;

    @ManyToMany(mappedBy = "farmacias")
    private List <Producto> productos;

    @ManyToMany(mappedBy = "farmacias")
    private List <Cliente> clientes;

    @ManyToMany(mappedBy = "farmacias")
    private List <Factura> facturas;



    public Farmacia(String nombre){
        this.nombre = nombre;
    }



}
