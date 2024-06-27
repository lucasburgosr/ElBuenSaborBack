package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Categoria;
import com.example.buensaborback.domain.entities.Sucursal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SucursalRepository extends BaseRepository<Sucursal, Long> {

    // Consulta para obtener una sucursal junto con sus promociones, basada en el ID de la sucursal
    //@Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.promociones WHERE s.id = :id")
    //Sucursal findWithPromocionesById(@Param("id") Long id);

    // Consulta para obtener una sucursal junto con sus empleados, basada en el ID de la sucursal
    //@Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.empleados WHERE s.id = :id")
    //Sucursal findWithEmpleadosById(@Param("id") Long id);

    // Consulta para obtener una sucursal junto con sus categorías, basada en el ID de la sucursal
    //@Query("SELECT s FROM Sucursal s LEFT JOIN FETCH s.categorias WHERE s.id = :id")
    //Sucursal findWithCategoriasById(@Param("id") Long id);

    // Consulta para obtener las categorías de una sucursal, basada en el ID de la sucursal
    @Query("SELECT s.categorias FROM Sucursal s WHERE s.id = :sucursalId")
    List<Categoria> findCategoriasBySucursalId(@Param("sucursalId") Long sucursalId);

    // Consulta para obtener todas las sucursales de una empresa, basada en el ID de la empresa
    @Query("SELECT s FROM Sucursal s WHERE s.empresa.id = :empresaId")
    List<Sucursal> findAllByEmpresaId(@Param("empresaId") Long empresaId);
}
