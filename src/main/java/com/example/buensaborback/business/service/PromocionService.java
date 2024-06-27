package com.example.buensaborback.business.service;


import com.example.buensaborback.business.service.Base.BaseService;
import com.example.buensaborback.domain.entities.Promocion;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PromocionService extends BaseService<Promocion, Long> {
    Set<Promocion> findBySucursalId(Long id);
}
