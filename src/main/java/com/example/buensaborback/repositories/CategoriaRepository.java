package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaRepository extends BaseRepository<Categoria,Long> {
    // Consulta para obtener una categoría junto con sus sucursales, basada en el ID de la categoría
    @Query("SELECT c FROM Categoria c LEFT JOIN FETCH c.sucursales WHERE c.id = :id")
    Categoria findWithSucursalesById(@Param("id") Long id);
}
