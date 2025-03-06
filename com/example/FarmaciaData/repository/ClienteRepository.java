package com.example.FarmaciaData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FarmaciaData.models.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNombreIn(List<String> nombres);
    Cliente findByNombre(String nombre);
    Cliente findByDni(String dni);

}

