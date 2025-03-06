package com.example.FarmaciaData.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.FarmaciaData.models.Producto;

import jakarta.transaction.Transactional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreIn(List<String> nombres);
    Producto findByCodigoBarras(String codigoBarras);
    Producto existsByCodigoBarras(String codigoBarras);
    Producto findByNombre(String nombre);

    
    @Modifying
    @Query("DELETE FROM Producto p WHERE p.codigoBarras = :codigoBarras")
        void deleteByCodigoBarras(@Param("codigoBarras") String codigoBarras);
    
    @Transactional
    @Modifying
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.codigoBarras = :codigoBarras AND p.stock >= :cantidad")
    int reducirStock(@Param("codigoBarras") String codigoBarras, @Param("cantidad") int cantidad);


}
