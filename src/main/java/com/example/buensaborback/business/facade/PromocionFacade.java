package com.example.buensaborback.business.facade;

import com.example.buensaborback.business.facade.Base.BaseFacade;
import com.example.buensaborback.business.facade.Base.BaseFacadeImpl;
import com.example.buensaborback.domain.dtos.PromocionDto;

import java.util.Set;

public interface PromocionFacade extends BaseFacade<PromocionDto, Long> {
    public Set<PromocionDto> findBySucursalId(Long sucursalId);
}
