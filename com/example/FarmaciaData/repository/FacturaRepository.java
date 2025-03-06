package com.example.FarmaciaData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.FarmaciaData.models.Cliente;
import com.example.FarmaciaData.models.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long>{
    

    void deleteAllByCliente(Cliente cliente);
    
    @Modifying
    @Query("DELETE FROM Factura f WHERE EXISTS (SELECT 1 FROM f.productos p WHERE p.codigoBarras = :codigoBarras)")
    void deleteByProductoId(@Param("codigoBarras") String codigoBarras);

}
