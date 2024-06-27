package com.example.buensaborback.repositories;

import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PromocionRepository extends BaseRepository<Promocion,Long> {
    @Query("SELECT p FROM Promocion p LEFT JOIN FETCH p.sucursales WHERE p.id = :id")
    Set<Promocion> findBySucursalId(@Param("id") Long id);
}
