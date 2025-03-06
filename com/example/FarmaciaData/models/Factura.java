package com.example.FarmaciaData.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name="facturas")
@AllArgsConstructor
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroFactura;
    private LocalDate fecha;
    @Column(name = "total_calculado")
    private Double totalCalculado;



    public Double calcularTotal() {
        if (productos == null || productos.isEmpty()) {
            return 0.0;
        }
        return productos.stream()
                        .filter(producto -> producto != null && producto.getPrecio() != null)
                        .mapToDouble(Producto::getPrecio)
                        .sum();
    }

    @PrePersist
    @PreUpdate
    public void actualizarTotalCalculado() {
        this.totalCalculado = calcularTotal();
    }

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id") 
    @JsonIgnore // Ignorar serialización de cliente para evitar ciclo infinito
    private Cliente cliente;

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
        name = "facturas_farmacias", 
        joinColumns = @JoinColumn(name = "factura_id"), 
        inverseJoinColumns = @JoinColumn(name = "farmacia_id"))
    @JsonIgnore // Ignorar serialización de farmacias para evitar ciclo infinito
    private List<Farmacia> farmacias;

    @ManyToMany(fetch = FetchType.EAGER) 
    @JoinTable(
        name = "facturas_productos", 
        joinColumns = @JoinColumn(name = "factura_id"), 
        inverseJoinColumns = @JoinColumn(name = "producto_id"))
    @JsonIgnore // Ignorar serialización de productos para evitar ciclo infinito
    private List<Producto> productos;


}
