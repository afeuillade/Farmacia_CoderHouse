package com.example.FarmaciaData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FarmaciaData.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombreIn(List<String> nombres);
    Cliente findByNombre(String nombre);
    public Cliente findByDni(String dni);


    @Modifying
    @Query("UPDATE Cliente c SET c.nombre = :nombre, c.apellido = :apellido WHERE c.dni = :dni")
    void updateCliente(@Param("dni") String dni, @Param("nombre") String nombre, @Param("apellido") String apellido);

}

