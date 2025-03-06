package com.example.FarmaciaData.models;

import java.time.LocalDate;
import java.util.List;

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

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cliente_id") 
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "facturas_productos", 
        joinColumns = @JoinColumn(name = "factura_id"), 
        inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos;

    @ManyToMany
    @JoinTable(
        name = "facturas_farmacias", 
        joinColumns = @JoinColumn(name = "factura_id"), 
        inverseJoinColumns = @JoinColumn(name = "farmacia_id"))
    private List<Farmacia> farmacias;


}
