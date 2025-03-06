package com.example.FarmaciaData.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name="clientes")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="cliente_id")
    private Long id;

    @Column(name ="NAME")
    private String nombre;
    private String apellido;
    @Column(name="cliente_dni")
    private String dni;
    private String direccion;
    private String telefono;

    
    private LocalDate fechaCrecion;
    private LocalDate fechaUpdate;
    


    @ManyToMany
    @JoinTable(name = "cliente_farmacia", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "farmacia_id"))
    private List<Farmacia> farmacias;

    @ManyToMany
    @JoinTable(name = "clientes_productos", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "producto_id"))private List<Producto> productos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Factura> facturas;
}
